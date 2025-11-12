package br.com.unifavip.cliente_pedidos.dto.client.input;

import br.com.unifavip.cliente_pedidos.models.client.ClientAddress;
import br.com.unifavip.cliente_pedidos.models.order.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class ClientInputDTO implements Serializable {
    @NotBlank(message = "Nome obrigatório!")
    @Size(min = 2, message = "Nome deve conter no mínimo 2 Caracteres")
    private String name;

    @NotBlank(message = "CPF obrigatório!")
    @Size(min = 1, message = "CPF deve conter no mínimo 2 Caracteres e no máximo 14", max = 14)
    private String cpf;

    private String telephone;
    private List<ClientAddressInputDTO> addresses = new ArrayList<>();
}
