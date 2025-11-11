package br.com.unifavip.cliente_pedidos.models.client;

import br.com.unifavip.cliente_pedidos.models.commons.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Client extends AbstractEntity {
    private static final String REGEX = "[^\\d]";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_sequence")
    @SequenceGenerator(name = "client_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "telephone")
    private String telephone;

    @Lob
    @Column(name = "address")
    private String address;

    @PreUpdate
    @PrePersist
    public void prePersist() {
        cpf = cpf.replaceAll(REGEX, "");
        telephone = telephone.replaceAll(REGEX, "");
    }
}
