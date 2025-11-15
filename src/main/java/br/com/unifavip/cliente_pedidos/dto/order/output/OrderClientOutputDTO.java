package br.com.unifavip.cliente_pedidos.dto.order.output;

import br.com.unifavip.cliente_pedidos.dto.client.output.ClientAddressOutputDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderClientOutputDTO implements Serializable {
    private Long id;
    private String name;
    private String cpf;
    private String telephone;
    private Boolean status;
    private List<ClientAddressOutputDTO> addresses = new ArrayList<>();
}
