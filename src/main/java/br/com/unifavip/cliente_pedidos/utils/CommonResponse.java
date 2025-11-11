package br.com.unifavip.cliente_pedidos.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {
    private int status;
    private Boolean error;
    private String message;
    private String detailMessage;
    private String timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public CommonResponse(T result, int status, Boolean error, String message, String detailMessage) {
        this.result = result;
        this.status = status;
        this.error = error;
        this.message = message;
        this.detailMessage = detailMessage;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public boolean hasError() {
        return Boolean.TRUE.equals(error);
    }

    public static CommonResponse<?> convertThrowableToCommonResponse(Throwable e) {
        e.printStackTrace();
        return CommonResponse.builder()
                .status(getStatusFromException(e))
                .error(Boolean.TRUE)
                .message(e.getMessage())
                .detailMessage(getDetailMessageFromException(e))
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }

    public static CommonResponse<?> convertThrowableToCommonResponse(Throwable e, Object result) {
        e.printStackTrace();
        return CommonResponse.builder()
                .status(getStatusFromException(e))
                .error(Boolean.TRUE)
                .result(result)
                .message(e.getMessage())
                .detailMessage(getDetailMessageFromException(e))
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }

    public static CommonResponse<?> convertThrowableToCommonResponseToTvs() {
        return CommonResponse.builder()
                .status(201)
                .error(Boolean.TRUE)
                .message("error totvs!")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }

    private static int getStatusFromException(Throwable e) {
        try {
            return (int) ((Object) e).getClass().getField("status").get(e);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            return HttpStatusConstants.HttpBadRequest.CODE;
        }
    }

    private static String getDetailMessageFromException(Throwable e) {
        try {
            return (String) ((Object) e).getClass().getField("detailMessage").get(e);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            return HttpStatusConstants.HttpBadRequest.DESCRIPTION;
        }
    }

    public static CommonResponse<?> ok() {
        return CommonResponse.builder()
                .result("ok")
                .error(Boolean.FALSE)
                .status(HttpStatusConstants.HTTP_CREATED.CODE)
                .message(HttpStatusConstants.HTTP_CREATED.DESCRIPTION)
                .detailMessage("received command ok" )
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build();
    }

    public static CommonResponse<?> created(Object object) {
        return CommonResponse.builder()
                .result(object)
                .error(Boolean.FALSE)
                .status(HttpStatusConstants.HTTP_CREATED.CODE)
                .message(HttpStatusConstants.HTTP_CREATED.DESCRIPTION)
                .detailMessage("Created" )
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build();
    }

    public static CommonResponse<?> updated(Object object) {
        return CommonResponse.builder()
                .result(object)
                .error(Boolean.FALSE)
                .status(HttpStatusConstants.HttpOK.CODE)
                .message(HttpStatusConstants.HttpOK.DESCRIPTION)
                .detailMessage("Updated")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build();
    }

    public static CommonResponse<?> founded(Object object) {
        return CommonResponse.builder()
                .result(object)
                .error(Boolean.FALSE)
                .status(HttpStatusConstants.HttpOK.CODE)
                .message(HttpStatusConstants.HttpOK.DESCRIPTION)
                .detailMessage("Query performed successfully!")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build();
    }
}
