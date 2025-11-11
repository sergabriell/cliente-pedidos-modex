package br.com.unifavip.cliente_pedidos.dto.user.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginInputDTO implements Serializable {
    private String email;
    private String password;
}
