package br.com.unifavip.cliente_pedidos.dto.order.input;

import br.com.unifavip.cliente_pedidos.dto.enums.order.OrderStatus;
import br.com.unifavip.cliente_pedidos.dto.enums.order.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindByFilterOrderInputDTO {
    private Long id;

    private Long clientId;

    private Long createdByUserId;

    private OrderStatus status;

    private PaymentType paymentType;

    private Long productId;

    private String productDescription;

    private Double totalPriceMin;
    private Double totalPriceMax;
}
