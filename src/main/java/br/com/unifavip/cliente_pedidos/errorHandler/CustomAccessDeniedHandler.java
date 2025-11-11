package br.com.unifavip.cliente_pedidos.errorHandler;

import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        CommonResponse<?> error = CommonResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error(true)
                .message("Você não tem permissão para acessar este recurso")
                .detailMessage(accessDeniedException.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(error)
        );
    }
}
