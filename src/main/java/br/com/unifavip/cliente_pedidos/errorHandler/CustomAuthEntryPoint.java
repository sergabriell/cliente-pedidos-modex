package br.com.unifavip.cliente_pedidos.errorHandler;

import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        CommonResponse<?> error = CommonResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(true)
                .message("Token inválido ou ausente")
                .detailMessage(authException.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(error)
        );
    }
}
