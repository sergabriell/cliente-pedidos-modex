package br.com.unifavip.cliente_pedidos.controller.user;

import br.com.unifavip.cliente_pedidos.dto.user.input.FindByFilterUserInputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.input.LoginInputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.input.UserInputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.input.UserUpdateInputDTO;
import br.com.unifavip.cliente_pedidos.services.user.UserService;
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
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody @Valid UserInputDTO dto) {
        CommonResponse<?> user = userService.create(dto);
        return ResponseEntity.status(user.getStatus()).body(user);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER_EDIT')")
    public ResponseEntity<?> update(@RequestBody @Valid UserUpdateInputDTO dto) {
        CommonResponse<?> user = userService.update(dto);
        return ResponseEntity.status(user.getStatus()).body(user);
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginInputDTO dto) {
        CommonResponse<?> user = userService.login(dto);
        return ResponseEntity.status(user.getStatus()).body(user);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER_VIEW_ALL')")
    public ResponseEntity<?> findUserByFilter(@ModelAttribute FindByFilterUserInputDTO dto,
                                              @PageableDefault(sort = {"id"}, direction = ASC) Pageable pageable) {
        CommonResponse<?> user = userService.findUserByFilter(dto, pageable);
        return ResponseEntity.status(user.getStatus()).body(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER_VIEW')")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        CommonResponse<?> user = userService.findById(id);
        return ResponseEntity.status(user.getStatus()).body(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER_DELETE')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        CommonResponse<?> user = userService.delete(id);
        return ResponseEntity.status(user.getStatus()).body(user);
    }
}
