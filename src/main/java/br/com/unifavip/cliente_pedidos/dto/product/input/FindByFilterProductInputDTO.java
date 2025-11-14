package br.com.unifavip.cliente_pedidos.dto.product.input;

import br.com.unifavip.cliente_pedidos.dto.enums.product.Color;
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
public class FindByFilterProductInputDTO implements Serializable {
    private Long id;

    private String description;

    private Boolean status;

    private Long productTypeId;

    private Double priceMin;
    private Double priceMax;

    private Integer quantityMin;
    private Integer quantityMax;

    private Integer sizeMin;
    private Integer sizeMax;

    private Color color;
}
