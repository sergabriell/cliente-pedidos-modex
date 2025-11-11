package br.com.unifavip.cliente_pedidos.dto.user.output.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRolesOutputDTO implements Serializable {
    private Long id;
    private String name;
    private String label;
}
