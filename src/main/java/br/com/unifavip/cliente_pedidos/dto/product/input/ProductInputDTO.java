package br.com.unifavip.cliente_pedidos.dto.product.input;

import br.com.unifavip.cliente_pedidos.dto.enums.product.Color;
import br.com.unifavip.cliente_pedidos.models.product.ProductType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInputDTO implements Serializable {
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
}
