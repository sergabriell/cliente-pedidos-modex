package br.com.unifavip.cliente_pedidos.controller.client;

import br.com.unifavip.cliente_pedidos.dto.client.input.ClientInputDTO;
import br.com.unifavip.cliente_pedidos.dto.client.input.ClientUpdateInputDTO;
import br.com.unifavip.cliente_pedidos.dto.client.input.FindByFilterClientInputDTO;
import br.com.unifavip.cliente_pedidos.services.client.ClientService;
import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/client")
public class ClientController {
    private final ClientService clientService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT_CREATE')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody @Valid ClientInputDTO dto) {
        CommonResponse<?> client = clientService.create(dto);
        return ResponseEntity.status(client.getStatus()).body(client);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT_UPDATE')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody @Valid ClientUpdateInputDTO dto) {
        CommonResponse<?> client = clientService.update(dto);
        return ResponseEntity.status(client.getStatus()).body(client);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT_VIEW')")
    public ResponseEntity<?> findClientByFilter(@ModelAttribute FindByFilterClientInputDTO dto,
                                                @PageableDefault(sort = {"id"}, direction = ASC) Pageable pageable) {
        CommonResponse<?> client = clientService.findClientByFilter(dto, pageable);
        return ResponseEntity.status(client.getStatus()).body(client);
    }
}
