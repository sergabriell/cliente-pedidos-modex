package br.com.unifavip.cliente_pedidos.exceptions.order;

import br.com.unifavip.cliente_pedidos.exceptions.client.ClientNotFoundException;
import br.com.unifavip.cliente_pedidos.models.client.Client;
import br.com.unifavip.cliente_pedidos.models.order.Order;
import br.com.unifavip.cliente_pedidos.utils.HttpStatusConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderNotFoundException extends RuntimeException {
    public final int status;
    public final String detailMessage;

    public OrderNotFoundException(Long id) {
        super("Not found order for id");
        log.error("Not found order for id: ".concat(String.valueOf(id)));
        this.status = HttpStatusConstants.HTTP_NOT_FOUND.CODE;
        this.detailMessage = HttpStatusConstants.HTTP_NOT_FOUND.DESCRIPTION;
    }

    public OrderNotFoundException() {
        super("Not found order");
        this.status = HttpStatusConstants.HTTP_NOT_FOUND.CODE;
        this.detailMessage = HttpStatusConstants.HTTP_NOT_FOUND.DESCRIPTION;
    }

    public static void throwNew(Order order) {
        throw new OrderNotFoundException(order.getId());
    }
}
