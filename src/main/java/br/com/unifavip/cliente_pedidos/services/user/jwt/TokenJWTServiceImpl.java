package br.com.unifavip.cliente_pedidos.services.user.jwt;

import br.com.unifavip.cliente_pedidos.configuration.jwt.JwtProperties;
import br.com.unifavip.cliente_pedidos.models.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenJWTServiceImpl implements TokenJWTService {
    private final JwtProperties jwtProperties;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("userId", user.getId())
                .withClaim("group", user.getUserGroup().getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .sign(algorithm);
    }

    @Override
    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());

        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();
    }
}
