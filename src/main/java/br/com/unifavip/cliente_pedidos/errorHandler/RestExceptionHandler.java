package br.com.unifavip.cliente_pedidos.errorHandler;

import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers,
                                                               HttpStatusCode status,
                                                               WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        CommonResponse<?> commonResponse = CommonResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(Boolean.TRUE)
                .message("Field(s) validation error(s): " + fields)
                .detailMessage(fieldMessages)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();

        return new ResponseEntity<>(commonResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             @Nullable Object body,
                                                             HttpHeaders headers,
                                                             HttpStatusCode status,
                                                             WebRequest request) {

        CommonResponse<?> commonResponse = CommonResponse.builder()
                .status(status.value())
                .error(Boolean.TRUE)
                .message(ex.getMessage())
                .detailMessage(ex.getClass().getName())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();

        return new ResponseEntity<>(commonResponse, headers, status);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        CommonResponse<?> commonResponse = CommonResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(Boolean.TRUE)
                .message(ex.getMessage())
                .detailMessage(ex.getClass().getName() + " - " + ex.getMostSpecificCause().getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();

        return new ResponseEntity<>(commonResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {

        CommonResponse<?> commonResponse = CommonResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(Boolean.TRUE)
                .message(ex.getMessage())
                .detailMessage(ex.getClass().getName())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();

        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {

        CommonResponse<?> commonResponse = CommonResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error(Boolean.TRUE)
                .message(ex.getMessage())
                .detailMessage(ex.getClass().getName())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();

        return new ResponseEntity<>(commonResponse, HttpStatus.FORBIDDEN);
    }
}
