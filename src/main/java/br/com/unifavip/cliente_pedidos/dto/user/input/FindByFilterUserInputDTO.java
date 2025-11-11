package br.com.unifavip.cliente_pedidos.dto.user.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindByFilterUserInputDTO implements Serializable {
    private Long id;

    private String name;

    private String email;

    private Boolean status;

    private Long userGroupId;
}
