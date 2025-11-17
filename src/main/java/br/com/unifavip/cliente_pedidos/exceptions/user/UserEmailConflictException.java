package br.com.unifavip.cliente_pedidos.exceptions.user;

import br.com.unifavip.cliente_pedidos.exceptions.product.ProductNotFoundException;
import br.com.unifavip.cliente_pedidos.models.user.User;
import br.com.unifavip.cliente_pedidos.utils.HttpStatusConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserEmailConflictException extends RuntimeException {
    public final int status;
    public final String detailMessage;

    public UserEmailConflictException(Long id) {
        super("Not User Email Conflict user for id");
        log.error("Not found user for id: ".concat(String.valueOf(id)));
        this.status = HttpStatusConstants.HTTP_CONFLICT.CODE;
        this.detailMessage = HttpStatusConstants.HTTP_CONFLICT.DESCRIPTION;
    }

    public UserEmailConflictException() {
        super("Not found user");
        this.status = HttpStatusConstants.HTTP_CONFLICT.CODE;
        this.detailMessage = HttpStatusConstants.HTTP_CONFLICT.DESCRIPTION;
    }

    public static void throwNew(User user) {
        throw new UserEmailConflictException(user.getId());
    }
}
