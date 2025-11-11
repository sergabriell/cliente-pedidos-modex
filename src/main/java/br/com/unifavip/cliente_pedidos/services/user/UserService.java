package br.com.unifavip.cliente_pedidos.services.user;

import br.com.unifavip.cliente_pedidos.dto.user.input.LoginInputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.input.UserInputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.UserOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.auth.LoginOutputDTO;
import br.com.unifavip.cliente_pedidos.models.user.User;

public interface UserService {
    UserOutputDTO create(UserInputDTO dto);

    LoginOutputDTO login (LoginInputDTO dto);
}
