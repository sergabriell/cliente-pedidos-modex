package br.com.unifavip.cliente_pedidos.exceptions.client;

import br.com.unifavip.cliente_pedidos.models.client.Client;
import br.com.unifavip.cliente_pedidos.utils.HttpStatusConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientNotFoundException extends RuntimeException {
    public final int status;
    public final String detailMessage;

    public ClientNotFoundException(Long id) {
        super("Not found client for id");
        log.error("Not found client for id: ".concat(String.valueOf(id)));
        this.status = HttpStatusConstants.HTTP_NOT_FOUND.CODE;
        this.detailMessage = HttpStatusConstants.HTTP_NOT_FOUND.DESCRIPTION;
    }

    public ClientNotFoundException() {
        super("Not found client");
        this.status = HttpStatusConstants.HTTP_NOT_FOUND.CODE;
        this.detailMessage = HttpStatusConstants.HTTP_NOT_FOUND.DESCRIPTION;
    }

    public static void throwNew(Client client) {
        throw new ClientNotFoundException(client.getId());
    }

}
