package br.com.unifavip.cliente_pedidos.services.user.jwt;

import br.com.unifavip.cliente_pedidos.models.user.User;

public interface TokenJWTService {
    String generateToken(User user);

    String validateToken(String token);
}
