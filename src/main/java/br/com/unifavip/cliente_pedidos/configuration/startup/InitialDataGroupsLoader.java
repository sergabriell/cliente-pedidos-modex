package br.com.unifavip.cliente_pedidos.configuration.startup;

import br.com.unifavip.cliente_pedidos.models.user.UserGroup;
import br.com.unifavip.cliente_pedidos.models.user.UserRole;
import br.com.unifavip.cliente_pedidos.repository.user.UserGroupRepository;
import br.com.unifavip.cliente_pedidos.repository.user.UserRepository;
import br.com.unifavip.cliente_pedidos.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class InitialDataGroupsLoader implements ApplicationRunner {
    private final UserGroupRepository userGroupRepository;
    private final UserRoleRepository userRoleRepository;

    public void run(ApplicationArguments args) {
        createGroupAdmin();
        createGroupEstoquista();
        createGroupAtendente();
    }

    private UserRole getRole(String name) {
        return userRoleRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new RuntimeException("Role não encontrada: " + name));
    }

    private void createGroupWithRoles(String name, boolean admin, Set<UserRole> roles) {
        userGroupRepository.findByNameIgnoreCase(name).ifPresentOrElse(
                (g) -> {},
                () -> {
                    UserGroup group = UserGroup.builder()
                            .name(name)
                            .admin(admin)
                            .status(true)
                            .observation("Criado automaticamente")
                            .userRoles(roles)
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .build();
                    userGroupRepository.save(group);
                }
        );
    }

    private void createGroupAdmin() {
        createGroupWithRoles("Administrador", true, Set.of(getRole("ROLE_ADMIN")));
    }

    private void createGroupEstoquista() {
        createGroupWithRoles("Estoquista", false, Set.of(
                getRole("ROLE_PRODUCT_CREATE"),
                getRole("ROLE_PRODUCT_VIEW"),
                getRole("ROLE_PRODUCT_EDIT"),
                getRole("ROLE_PRODUCT_DELETE"),
                getRole("ROLE_USER_VIEW"),
                getRole("ROLE_USER_EDIT"),
                getRole("ROLE_USER_DELETE")
        ));
    }

    private void createGroupAtendente() {
        createGroupWithRoles("Atendente", false, Set.of(
                getRole("ROLE_ORDER_CREATE"),
                getRole("ROLE_ORDER_VIEW"),
                getRole("ROLE_ORDER_EDIT"),
                getRole("ROLE_ORDER_DELETE"),

                getRole("ROLE_CLIENT_CREATE"),
                getRole("ROLE_CLIENT_VIEW"),
                getRole("ROLE_CLIENT_EDIT"),
                getRole("ROLE_CLIENT_DELETE"),

                getRole("ROLE_PRODUCT_VIEW"),
                getRole("ROLE_USER_VIEW"),
                getRole("ROLE_USER_EDIT"),
                getRole("ROLE_USER_DELETE")
        ));
    }
}
