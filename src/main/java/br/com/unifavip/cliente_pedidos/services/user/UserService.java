package br.com.unifavip.cliente_pedidos.services.user;

import br.com.unifavip.cliente_pedidos.dto.user.input.FindByFilterUserInputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.input.LoginInputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.input.UserInputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.input.UserUpdateInputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.UserOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.user.output.auth.LoginOutputDTO;
import br.com.unifavip.cliente_pedidos.models.user.User;
import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    CommonResponse<?> create(UserInputDTO dto);

    CommonResponse<?> update(UserUpdateInputDTO dto);

    CommonResponse<?> login (LoginInputDTO dto);

    CommonResponse<?> listUsers();

    CommonResponse<?> findById(Long id);

    CommonResponse<?> delete(Long id);

    CommonResponse<?> findUserByFilter(FindByFilterUserInputDTO dto, Pageable pageable);

    Page<User> findByFilter(FindByFilterUserInputDTO dto, Pageable pageable);
}
