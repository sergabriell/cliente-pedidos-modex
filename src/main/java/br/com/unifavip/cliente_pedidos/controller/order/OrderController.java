package br.com.unifavip.cliente_pedidos.controller.order;

import br.com.unifavip.cliente_pedidos.dto.order.input.FindByFilterOrderInputDTO;
import br.com.unifavip.cliente_pedidos.dto.order.input.OrderInputDTO;
import br.com.unifavip.cliente_pedidos.dto.order.input.OrderUpdateInputDTO;
import br.com.unifavip.cliente_pedidos.services.order.OrderService;
import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ORDER_CREATE')")
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> create(
            @Valid @RequestBody OrderInputDTO dto
    ) {
        CommonResponse<?> response = orderService.create(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ORDER_UPDATE')")
    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> update(
            @Valid @RequestBody OrderUpdateInputDTO dto
    ) {
        CommonResponse<?> response = orderService.update(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ORDER_VIEW')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByFilter(
            @ModelAttribute FindByFilterOrderInputDTO dto,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        CommonResponse<?> response = orderService.findOrderByFilter(dto, pageable);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ORDER_VIEW')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        CommonResponse<?> response = orderService.findById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ORDER_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        CommonResponse<?> response = orderService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
