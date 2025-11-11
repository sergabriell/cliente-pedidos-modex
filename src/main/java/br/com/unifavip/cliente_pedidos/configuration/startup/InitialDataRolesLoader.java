package br.com.unifavip.cliente_pedidos.configuration.startup;

import br.com.unifavip.cliente_pedidos.models.user.UserRole;
import br.com.unifavip.cliente_pedidos.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InitialDataRolesLoader implements ApplicationRunner {
    private final UserRoleRepository userRoleRepository;

    private static final Map<String, String> ROLES_MAP = Map.ofEntries(
            Map.entry("ROLE_ORDER_CREATE", "Criar Pedido"),
            Map.entry("ROLE_ORDER_VIEW", "Visualizar Pedido"),
            Map.entry("ROLE_ORDER_EDIT", "Editar Pedido"),
            Map.entry("ROLE_ORDER_DELETE", "Excluir Pedido"),

            Map.entry("ROLE_PRODUCT_CREATE", "Criar Produto"),
            Map.entry("ROLE_PRODUCT_VIEW", "Visualizar Produto"),
            Map.entry("ROLE_PRODUCT_EDIT", "Editar Produto"),
            Map.entry("ROLE_PRODUCT_DELETE", "Excluir Produto"),

            Map.entry("ROLE_CLIENT_CREATE", "Criar Cliente"),
            Map.entry("ROLE_CLIENT_VIEW", "Visualizar Cliente"),
            Map.entry("ROLE_CLIENT_EDIT", "Editar Cliente"),
            Map.entry("ROLE_CLIENT_DELETE", "Excluir Cliente"),

            Map.entry("ROLE_USER_VIEW", "Visualizar Usuario"),
            Map.entry("ROLE_USER_VIEW_ALL", "Visualizar todos os Usuarios"),
            Map.entry("ROLE_USER_EDIT", "Editar Usuario"),
            Map.entry("ROLE_USER_DELETE", "Excluir Usuario"),

            Map.entry("ROLE_ADMIN", "Administrador")
    );

    @Override
    public void run(ApplicationArguments args) {
        ROLES_MAP.forEach(this::createRoleIfNotExists);
    }

    private void createRoleIfNotExists(String name, String label) {
        userRoleRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    UserRole role = UserRole.builder()
                            .name(name)
                            .label(label)
                            .userGroups(new HashSet<>())
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .build();
                    return userRoleRepository.save(role);
                });
    }
}
