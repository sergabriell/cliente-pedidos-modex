package br.com.unifavip.cliente_pedidos.dto.user.output.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthOutputDTO implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String group;
    private List<String> roles;
}
