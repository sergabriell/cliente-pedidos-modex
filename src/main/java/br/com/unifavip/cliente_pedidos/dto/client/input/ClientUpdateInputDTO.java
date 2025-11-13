package br.com.unifavip.cliente_pedidos.dto.client.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ClientUpdateInputDTO implements Serializable {
    @NotNull(message = "Id obrigatório!")
    private Long id;

    @NotBlank(message = "Nome obrigatório!")
    @Size(min = 2, message = "Nome deve conter no mínimo 2 Caracteres")
    private String name;

    @NotBlank(message = "CPF obrigatório!")
    @Size(min = 1, message = "CPF deve conter no mínimo 2 Caracteres e no máximo 14", max = 14)
    private String cpf;

    private Boolean status;

    private String telephone;

    private List<ClientAddressUpdateInputDTO> addresses = new ArrayList<>();
}
