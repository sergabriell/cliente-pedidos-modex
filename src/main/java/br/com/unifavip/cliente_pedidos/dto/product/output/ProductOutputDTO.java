package br.com.unifavip.cliente_pedidos.dto.product.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOutputDTO implements Serializable {
    private Long id;
    private String description;
    private Double price;
    private Integer quantity;
    private Boolean status;
    private ProductTypeOutputDTO productType;
}
