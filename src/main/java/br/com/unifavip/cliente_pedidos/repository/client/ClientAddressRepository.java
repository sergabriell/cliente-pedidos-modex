package br.com.unifavip.cliente_pedidos.repository.client;

import br.com.unifavip.cliente_pedidos.models.client.ClientAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientAddressRepository extends JpaRepository<ClientAddress, Long>, JpaSpecificationExecutor<ClientAddress> {
}
