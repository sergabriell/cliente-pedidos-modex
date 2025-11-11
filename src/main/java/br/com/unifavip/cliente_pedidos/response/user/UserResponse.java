package br.com.unifavip.cliente_pedidos.response.user;

import br.com.unifavip.cliente_pedidos.dto.user.output.UserOutputDTO;
import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import br.com.unifavip.cliente_pedidos.utils.HttpStatusConstants;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class UserResponse {
    public static CommonResponse<?> created(UserOutputDTO dto) {
        return CommonResponse.builder()
                .result(dto)
                .error(Boolean.FALSE)
                .status(HttpStatusConstants.HTTP_CREATED.CODE)
                .message(HttpStatusConstants.HTTP_CREATED.DESCRIPTION)
                .detailMessage("Created user id: " + dto.getId())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build();
    }

    public static CommonResponse<?> updated(UserOutputDTO dto) {
        return CommonResponse.builder()
                .result(dto)
                .error(Boolean.FALSE)
                .status(HttpStatusConstants.HttpOK.CODE)
                .message(HttpStatusConstants.HttpOK.DESCRIPTION)
                .detailMessage("Updated user id: " + dto.getId())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build();
    }

    public static CommonResponse<?> founded(UserOutputDTO dto) {
        return CommonResponse.builder()
                .result(dto)
                .error(Boolean.FALSE)
                .status(HttpStatusConstants.HttpOK.CODE)
                .message(HttpStatusConstants.HttpOK.DESCRIPTION)
                .detailMessage("Query performed successfully!")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build();
    }

    public static CommonResponse<?> founded(Page<UserOutputDTO> dto) {
        return CommonResponse.builder()
                .result(dto)
                .error(Boolean.FALSE)
                .status(HttpStatusConstants.HttpOK.CODE)
                .message(HttpStatusConstants.HttpOK.DESCRIPTION)
                .detailMessage("Query performed successfully")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build();
    }

    public static CommonResponse<?> ok() {
        return CommonResponse.builder()
                .error(Boolean.FALSE)
                .status(HttpStatusConstants.HttpOK.CODE)
                .message(HttpStatusConstants.HttpOK.DESCRIPTION)
                .detailMessage("Accepted.")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build();
    }

    public static CommonResponse<?> ok(Object object) {
        return CommonResponse.builder()
                .result(object)
                .error(Boolean.FALSE)
                .status(HttpStatusConstants.HttpOK.CODE)
                .message(HttpStatusConstants.HttpOK.DESCRIPTION)
                .detailMessage("Accepted.")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build();
    }
}
