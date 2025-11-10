package br.com.unifavip.cliente_pedidos.models.client;

import br.com.unifavip.cliente_pedidos.models.commons.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_address")
public class ClientAddress extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_address_sequence")
    @SequenceGenerator(name = "client_address_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "public_place")
    private String publicPlace;

    @Column(name = "number")
    private String number;

    @Column(name = "complement")
    private String complement;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @PreUpdate
    @PrePersist
    public void preSave() {
        zipCode = zipCode.replaceAll("[^\\d]", "");
    }
}
