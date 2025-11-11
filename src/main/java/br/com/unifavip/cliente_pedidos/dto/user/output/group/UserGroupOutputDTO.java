package br.com.unifavip.cliente_pedidos.dto.user.output.group;

import br.com.unifavip.cliente_pedidos.dto.user.output.role.UserRolesOutputDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupOutputDTO implements Serializable {
    private Long id;
    private String name;
    private boolean admin;
    private boolean status;
    private String observation;
    private List<UserRolesOutputDTO> roles;
}
