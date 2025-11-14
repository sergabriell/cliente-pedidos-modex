package br.com.unifavip.cliente_pedidos.models.product;

import br.com.unifavip.cliente_pedidos.models.commons.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_type")
public class ProductType extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_type_sequence")
    @SequenceGenerator(name = "product_type_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "type_key", nullable = false, unique = true)
    private String typeKey;

    @Column(name = "description", nullable = false)
    private String description;
}
