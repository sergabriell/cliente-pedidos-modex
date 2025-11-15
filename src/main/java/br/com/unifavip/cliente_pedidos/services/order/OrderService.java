package br.com.unifavip.cliente_pedidos.services.order;

import br.com.unifavip.cliente_pedidos.dto.order.input.FindByFilterOrderInputDTO;
import br.com.unifavip.cliente_pedidos.dto.order.input.OrderInputDTO;
import br.com.unifavip.cliente_pedidos.dto.order.input.OrderUpdateInputDTO;
import br.com.unifavip.cliente_pedidos.models.order.Order;
import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {
    @Transactional
    CommonResponse<?> create(OrderInputDTO dto);

    @Transactional
    CommonResponse<?> update(OrderUpdateInputDTO dto);

    CommonResponse<?> findById(Long id);

    Page<Order> findByFilter(FindByFilterOrderInputDTO dto, Pageable pageable);

    CommonResponse<?> findOrderByFilter(FindByFilterOrderInputDTO dto, Pageable pageable);

    @Transactional
    CommonResponse<?> delete(Long id);
}
