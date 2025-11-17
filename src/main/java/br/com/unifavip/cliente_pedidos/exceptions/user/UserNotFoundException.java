package br.com.unifavip.cliente_pedidos.exceptions.user;

import br.com.unifavip.cliente_pedidos.exceptions.product.ProductNotFoundException;
import br.com.unifavip.cliente_pedidos.models.product.Product;
import br.com.unifavip.cliente_pedidos.models.user.User;
import br.com.unifavip.cliente_pedidos.utils.HttpStatusConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotFoundException extends RuntimeException {
    public final int status;
    public final String detailMessage;

    public UserNotFoundException(Long id) {
        super("Not found user for id");
        log.error("Not found user for id: ".concat(String.valueOf(id)));
        this.status = HttpStatusConstants.HTTP_NOT_FOUND.CODE;
        this.detailMessage = HttpStatusConstants.HTTP_NOT_FOUND.DESCRIPTION;
    }

    public UserNotFoundException() {
        super("Not found user");
        this.status = HttpStatusConstants.HTTP_NOT_FOUND.CODE;
        this.detailMessage = HttpStatusConstants.HTTP_NOT_FOUND.DESCRIPTION;
    }

    public static void throwNew(User user) {
        throw new UserNotFoundException(user.getId());
    }
}
