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
public class ProductTypeOutputDTO implements Serializable {
    private Long id;
    private String typeKey;
    private String description;
}
