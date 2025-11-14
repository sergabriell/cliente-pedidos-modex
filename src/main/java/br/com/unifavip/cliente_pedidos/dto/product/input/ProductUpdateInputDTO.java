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
public class ProductUpdateInputDTO implements Serializable {
    @NotNull
    private Long id;

    @NotNull
    private String description;

    @NotNull
    private Double price;

    @NotNull
    private Integer quantity;

    private Long productTypeId;

    @NotNull
    private Boolean status;

    private Integer size;

    private Color color;

    private String imageUrl;
}
