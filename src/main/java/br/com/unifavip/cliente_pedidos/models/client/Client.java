package br.com.unifavip.cliente_pedidos.models.client;

import br.com.unifavip.cliente_pedidos.models.commons.AbstractEntity;
import br.com.unifavip.cliente_pedidos.models.order.Order;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ClientAddress> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @PreUpdate
    @PrePersist
    public void prePersist() {
        cpf = cpf.replaceAll(REGEX, "");
        telephone = telephone.replaceAll(REGEX, "");
    }
}
