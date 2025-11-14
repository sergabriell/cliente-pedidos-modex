package br.com.unifavip.cliente_pedidos.dto.client.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindByFilterClientInputDTO implements Serializable {
    private Long id;

    private String name;

    private String cpf;

    private Boolean status;
}
