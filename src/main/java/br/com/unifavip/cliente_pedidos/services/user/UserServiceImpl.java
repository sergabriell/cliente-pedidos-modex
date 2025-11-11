package br.com.unifavip.cliente_pedidos.services.user;

import br.com.unifavip.cliente_pedidos.dto.user.input.LoginInputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.input.UserInputDTO;
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
import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
            log.info("Creating new user {}", dto.getName());
            userRepository.findByEmail(dto.getEmail())
                    .ifPresent(user -> {
                        throw new RuntimeException("E-mail já utilizado!");
                    });

            UserGroup group = userGroupRepository.findById(dto.getUserGroupId())
                    .orElseThrow(() -> new RuntimeException("UserGroup inválido"));//TODO exception de grupo não encontrado)

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
    public CommonResponse<?> login(LoginInputDTO dto) {
        try {
            log.info("Login email: {}", dto.getEmail());
            User user = userRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new RuntimeException("E-mail ou senha incorretos!"));

            if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                throw new RuntimeException("E-mail ou senha incorretos!");
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
        List<User> users = userRepository.findAll();
        List<UserOutputDTO> usersOutput = users.stream().map(this::userToOutputDTO).toList();
        return ok(usersOutput);
    }

    private UserOutputDTO userToOutputDTO(User user) {
        log.info("UserToOutputDTO: {}", user.getEmail());
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
