package br.com.unifavip.cliente_pedidos.exceptions.product;

import br.com.unifavip.cliente_pedidos.exceptions.order.OrderNotFoundException;
import br.com.unifavip.cliente_pedidos.models.order.Order;
import br.com.unifavip.cliente_pedidos.models.product.Product;
import br.com.unifavip.cliente_pedidos.utils.HttpStatusConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductNotFoundException extends RuntimeException {
    public final int status;
    public final String detailMessage;

    public ProductNotFoundException(Long id) {
        super("Not found product for id");
        log.error("Not found product for id: ".concat(String.valueOf(id)));
        this.status = HttpStatusConstants.HTTP_NOT_FOUND.CODE;
        this.detailMessage = HttpStatusConstants.HTTP_NOT_FOUND.DESCRIPTION;
    }

    public ProductNotFoundException() {
        super("Not found product");
        this.status = HttpStatusConstants.HTTP_NOT_FOUND.CODE;
        this.detailMessage = HttpStatusConstants.HTTP_NOT_FOUND.DESCRIPTION;
    }

    public static void throwNew(Product product) {
        throw new ProductNotFoundException(product.getId());
    }
}
