package br.com.unifavip.cliente_pedidos.dto.user.output;

import br.com.unifavip.cliente_pedidos.dto.user.output.group.UserGroupOutputDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOutputDTO implements Serializable {
    private Long id;
    private String name;
    private String email;
    private boolean status;
    private UserGroupOutputDTO group;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
