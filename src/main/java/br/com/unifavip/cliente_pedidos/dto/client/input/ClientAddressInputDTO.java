package br.com.unifavip.cliente_pedidos.dto.client.input;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientAddressInputDTO implements Serializable {
    private String zipCode;

    private String publicPlace;

    private String number;

    private String complement;

    private String neighborhood;

    private String city;

    private String state;

    private String country;
}
