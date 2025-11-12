package br.com.unifavip.cliente_pedidos.dto.order.output;

import br.com.unifavip.cliente_pedidos.dto.enums.order.OrderStatus;
import br.com.unifavip.cliente_pedidos.dto.enums.order.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderOutputDTO implements Serializable {
    private Long id;
    private String observation;
    private PaymentType paymentType;
    private OrderStatus status;
    private List<OrderItemOutputDTO> items;
}