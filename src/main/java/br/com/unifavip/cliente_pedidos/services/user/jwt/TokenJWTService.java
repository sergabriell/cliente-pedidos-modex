package br.com.unifavip.cliente_pedidos.services.user.jwt;

import br.com.unifavip.cliente_pedidos.models.user.User;

import java.util.List;

public interface TokenJWTService {
    String generateToken(User user, List<String> roles);

    String validateToken(String token);
}
