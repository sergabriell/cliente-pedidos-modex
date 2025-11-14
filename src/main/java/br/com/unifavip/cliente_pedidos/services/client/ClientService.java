package br.com.unifavip.cliente_pedidos.services.client;

import br.com.unifavip.cliente_pedidos.dto.client.input.ClientInputDTO;
import br.com.unifavip.cliente_pedidos.dto.client.input.ClientUpdateInputDTO;
import br.com.unifavip.cliente_pedidos.dto.client.input.FindByFilterClientInputDTO;
import br.com.unifavip.cliente_pedidos.models.client.Client;
import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface ClientService {
    @Transactional
    CommonResponse<?> create(ClientInputDTO clientInputDTO);

    @Transactional
    CommonResponse<?> update(ClientUpdateInputDTO dto);

    CommonResponse<?> findClientByFilter(FindByFilterClientInputDTO dto, Pageable pageable);

    Page<Client> findByFilter(FindByFilterClientInputDTO dto, Pageable pageable);

    CommonResponse<?> delete(Long id);

    CommonResponse<?> findById(Long id);
}
