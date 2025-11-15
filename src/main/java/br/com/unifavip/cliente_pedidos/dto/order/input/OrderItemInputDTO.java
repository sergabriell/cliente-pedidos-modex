package br.com.unifavip.cliente_pedidos.dto.order.input;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemInputDTO implements Serializable {
    @NotNull
    private Long productId;
    @NotNull
    private Integer quantity;
}
