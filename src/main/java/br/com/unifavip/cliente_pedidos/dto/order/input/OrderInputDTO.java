package br.com.unifavip.cliente_pedidos.dto.order.input;

import br.com.unifavip.cliente_pedidos.dto.enums.order.OrderStatus;
import br.com.unifavip.cliente_pedidos.dto.enums.order.PaymentType;
import jakarta.validation.constraints.NotNull;
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
public class OrderInputDTO implements Serializable {

    @NotNull
    private Long clientId;

    private String observation;

    @NotNull
    private PaymentType paymentType;

    @NotNull
    private List<OrderItemInputDTO> items;
}
