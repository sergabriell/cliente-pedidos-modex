package br.com.unifavip.cliente_pedidos.services.user;

import br.com.unifavip.cliente_pedidos.dto.user.input.FindByFilterUserInputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.input.LoginInputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.input.UserInputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.input.UserUpdateInputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.UserOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.auth.AuthOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.auth.LoginOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.group.UserGroupOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.role.UserRolesOutputDTO;
import br.com.unifavip.cliente_pedidos.models.user.User;
import br.com.unifavip.cliente_pedidos.models.user.UserGroup;
import br.com.unifavip.cliente_pedidos.models.user.UserRole;
import br.com.unifavip.cliente_pedidos.repository.user.UserGroupRepository;
import br.com.unifavip.cliente_pedidos.repository.user.UserRepository;
import br.com.unifavip.cliente_pedidos.services.user.jwt.TokenJWTService;
import br.com.unifavip.cliente_pedidos.specifications.user.UserSpecification;
import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static br.com.unifavip.cliente_pedidos.response.user.UserResponse.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenJWTService tokenService;
    private final ModelMapper modelMapper;

    @Override
    public CommonResponse<?> create(UserInputDTO dto) {
        try {
            log.info("UserServiceImpl Creating new user {}", dto.getName());
            userRepository.findByEmail(dto.getEmail())
                    .ifPresent(user -> {
                        throw new RuntimeException("E-mail já utilizado!");
                    });

            UserGroup group = userGroupRepository.findById(dto.getUserGroupId())
                    .orElseThrow(() -> new RuntimeException("UserGroup inválido"));

            UserGroupOutputDTO groupOutput = modelMapper.map(group, UserGroupOutputDTO.class);
            groupOutput.setRoles(
                    group.getUserRoles().stream()
                            .map(userRole -> modelMapper.map(userRole, UserRolesOutputDTO.class))
                            .collect(Collectors.toList())
            );

            User user = User.builder()
                    .name(dto.getName())
                    .email(dto.getEmail())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .status(dto.isStatus())
                    .userGroup(group)
                    .build();

            User save = userRepository.save(user);
            UserOutputDTO userOutput = modelMapper.map(save, UserOutputDTO.class);
            userOutput.setGroup(groupOutput);
            return created(userOutput);
        } catch (Exception e) {
            return CommonResponse.convertThrowableToCommonResponse(e);
        }
    }

    @Override
    public CommonResponse<?> update(UserUpdateInputDTO dto) {
        try {
            log.info("UserServiceImpl Update user {}", dto.getName());
            userRepository.findByEmail(dto.getEmail())
                    .filter(user -> !user.getId().equals(dto.getId()))
                    .ifPresent(user -> {
                        throw new RuntimeException("E-mail já utilizado!");
                    });
            UserGroup group = null;
            UserGroupOutputDTO groupOutput = null;
            if (Objects.nonNull(dto.getUserGroupId())) {
                group = userGroupRepository.findById(dto.getUserGroupId())
                        .orElseThrow(() -> new RuntimeException("UserGroup inválido"));

                groupOutput = modelMapper.map(group, UserGroupOutputDTO.class);
                groupOutput.setRoles(
                        group.getUserRoles().stream()
                                .map(userRole -> modelMapper.map(userRole, UserRolesOutputDTO.class))
                                .collect(Collectors.toList())
                );
            }

            User userById = userRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("User not found"));
            userById.setEmail(dto.getEmail());
            if (dto.getPassword() != null) {
                userById.setPassword(passwordEncoder.encode(dto.getPassword()));
            }
            userById.setStatus(dto.isStatus());
            userById.setUserGroup(group);
            userById.setName(dto.getName());

            User save = userRepository.save(userById);
            UserOutputDTO userOutput = modelMapper.map(save, UserOutputDTO.class);
            userOutput.setGroup(groupOutput);
            return updated(userOutput);
        } catch (Exception e) {
            return CommonResponse.convertThrowableToCommonResponse(e);
        }
    }

    @Override
    public CommonResponse<?> login(LoginInputDTO dto) {
        try {
            log.info("UserServiceImpl login: {}", dto.getEmail());
            User user = userRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new RuntimeException("E-mail ou senha incorretos!"));

            if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                throw new RuntimeException("E-mail ou senha incorretos!");
            }
            if (!user.isStatus()) {
                if (user.getUserGroup().isAdmin()) {
                    throw new RuntimeException("Admin desativado. Contacte o suporte.");
                } else {
                    throw new RuntimeException("Seu usuário está desativado. Contacte um administrador!");
                }
            }

            AuthOutputDTO userDTO = new AuthOutputDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getUserGroup().getName(),
                    user.getUserGroup().getUserRoles().stream()
                            .map(UserRole::getName)
                            .collect(Collectors.toList())
            );

            String token = tokenService.generateToken(user, userDTO.getRoles());
            LoginOutputDTO loginOutput = new LoginOutputDTO(token, userDTO);
            log.info("Login token: {}", token);
            return ok(loginOutput);
        } catch (Exception e) {
            return CommonResponse.convertThrowableToCommonResponse(e);
        }
    }

    @Override
    public CommonResponse<?> listUsers() {
        log.info("UserServiceImpl listUsers");
        List<User> users = userRepository.findAll();
        List<UserOutputDTO> usersOutput = users.stream().map(this::userToOutputDTO).toList();
        return ok(usersOutput);
    }

    @Override
    public CommonResponse<?> findById(Long id) {
        log.info("UserServiceImpl findById: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));

        return founded(userToOutputDTO(user));
    }

    @Override
    public CommonResponse<?> delete(Long id) {
        log.info("UserServiceImpl delete: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));

        user.setStatus(false);
        userRepository.save(user);
        return ok(userToOutputDTO(user));
    }

    @Override
    public CommonResponse<?> findUserByFilter(FindByFilterUserInputDTO dto, Pageable pageable) {
        log.info("UserServiceImpl findUserByFilter: {}", dto);
        try {
            Page<User> userPage = findByFilter(dto, pageable);
            PageImpl<UserOutputDTO> userOutputDTOS = new PageImpl<>(
                    userPage.stream()
                            .filter(Objects::nonNull)
                            .map(this::userToOutputDTO)
                            .toList(), userPage.getPageable(), userPage.getTotalElements());

            return founded(userOutputDTOS);
        } catch (Exception e) {
            return CommonResponse.convertThrowableToCommonResponse(e);
        }
    }

    @Override
    public Page<User> findByFilter(FindByFilterUserInputDTO dto, Pageable pageable) {
        log.info("UserServiceImpl findByFilter: {}", dto);
        Specification<User> specification = Specification.anyOf(
                UserSpecification.idEquals(dto.getId()),
                UserSpecification.emailLike(dto.getEmail()),
                UserSpecification.nameLike(dto.getName()),
                UserSpecification.statusEquals(dto.getStatus()),
                UserSpecification.userGroupIdEquals(dto.getUserGroupId())
        );

        return userRepository.findAll(specification, pageable);
    }

    private UserOutputDTO userToOutputDTO(User user) {
        log.info("UserServiceImpl UserToOutputDTO: {}", user.getEmail());
        UserOutputDTO userOutput = modelMapper.map(user, UserOutputDTO.class);
        UserGroupOutputDTO userGroupOutput = modelMapper.map(user.getUserGroup(), UserGroupOutputDTO.class);
        userGroupOutput.setRoles(
                user.getUserGroup().getUserRoles().stream()
                        .map(userRole -> modelMapper.map(userRole, UserRolesOutputDTO.class))
                        .collect(Collectors.toList())
        );
        userOutput.setGroup(userGroupOutput);
        return userOutput;
    }
}
