package br.com.unifavip.cliente_pedidos.dto.client.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientAddressOutputDTO implements Serializable {
    private Long id;
    private String zipCode;
    private String publicPlace;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
}
