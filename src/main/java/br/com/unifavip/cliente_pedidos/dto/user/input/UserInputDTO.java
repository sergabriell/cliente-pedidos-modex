package br.com.unifavip.cliente_pedidos.dto.user.input;

import br.com.unifavip.cliente_pedidos.models.user.UserGroup;
import jakarta.validation.constraints.*;
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
    @Size(min = 2, message = "Nome deve conter no mínimo 2 Caracteres")
    private String name;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail obrigatório!")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Formato de e-mail inválido"
    )
    private String email;

    @NotBlank(message = "Senha obrigatória!")
    @Size(min = 6, message = "Senha deve conter no mínimo 6 Caracteres")
    private String password;

    private boolean status;

    @NotNull(message = "Grupo obrigatório!")
    private Long userGroupId;
}
