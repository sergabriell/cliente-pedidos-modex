package br.com.unifavip.cliente_pedidos.dto.user.output.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginOutputDTO implements Serializable {
    private String token;
    private AuthOutputDTO user;
}
