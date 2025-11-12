package br.com.unifavip.cliente_pedidos.dto.order.output;

import br.com.unifavip.cliente_pedidos.dto.product.output.ProductOutputDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemOutputDTO implements Serializable {
    private Long id;
    private Integer quantity;
    private Double priceAtPurchase;
    private ProductOutputDTO product;
}
