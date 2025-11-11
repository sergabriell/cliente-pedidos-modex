package br.com.unifavip.cliente_pedidos.dto.user.input;

import br.com.unifavip.cliente_pedidos.models.user.UserGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInputDTO implements Serializable {
    @NotBlank(message = "Nome obrigatório!")
    @NotNull(message = "Nome obrigatório!")
    private String name;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail obrigatório!")
    @NotNull(message = "E-mail obrigatório!")
    private String email;

    @NotBlank(message = "Senha obrigatória!")
    @NotNull(message = "Senha obrigatória!")
    @Size(min = 6, message = "Senha deve conter no mínimo 6 caractéres")
    private String password;

    private boolean status;

    @NotNull(message = "Grupo obrigatório!")
    private Long userGroupId;
}
