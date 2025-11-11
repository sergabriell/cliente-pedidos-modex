package br.com.unifavip.cliente_pedidos.configuration.jwt;

import br.com.unifavip.cliente_pedidos.dto.user.output.auth.AuthOutputDTO;
import br.com.unifavip.cliente_pedidos.models.user.User;
import br.com.unifavip.cliente_pedidos.models.user.UserRole;
import br.com.unifavip.cliente_pedidos.repository.user.UserRepository;
import br.com.unifavip.cliente_pedidos.services.user.TokenJWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final TokenJWTService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            String email = tokenService.validateToken(token);

            if (email != null) {
                User user = userRepository.findByEmail(email).get();

                AuthOutputDTO userDTO = new AuthOutputDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getUserGroup().getName(),
                        user.getUserGroup().getUserRoles().stream()
                                .map(UserRole::getName)
                                .collect(Collectors.toList())
                );

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDTO,
                                null,
                                userDTO.getRoles().stream()
                                        .map(SimpleGrantedAuthority::new)
                                        .collect(Collectors.toList())
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
