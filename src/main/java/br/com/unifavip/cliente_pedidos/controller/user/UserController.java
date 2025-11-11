package br.com.unifavip.cliente_pedidos.controller.user;

import br.com.unifavip.cliente_pedidos.dto.user.input.LoginInputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.input.UserInputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.UserOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.auth.LoginOutputDTO;
import br.com.unifavip.cliente_pedidos.models.user.User;
import br.com.unifavip.cliente_pedidos.services.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserOutputDTO> register(@RequestBody @Valid UserInputDTO dto) {
        UserOutputDTO user = userService.create(dto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginOutputDTO> login(@RequestBody LoginInputDTO dto) {
        LoginOutputDTO user = userService.login(dto);
        return ResponseEntity.ok(user);
    }
}
