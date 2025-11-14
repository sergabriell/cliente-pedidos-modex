package br.com.unifavip.cliente_pedidos.services.product;

import br.com.unifavip.cliente_pedidos.dto.product.input.FindByFilterProductInputDTO;
import br.com.unifavip.cliente_pedidos.dto.product.input.ProductInputDTO;
import br.com.unifavip.cliente_pedidos.dto.product.input.ProductUpdateInputDTO;
import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    @Transactional
    CommonResponse<?> create(ProductInputDTO dto, MultipartFile file);

    @Transactional
    CommonResponse<?> update(ProductUpdateInputDTO dto, MultipartFile file);

    CommonResponse<?> findById(Long id);

    CommonResponse<?> findByFilter(FindByFilterProductInputDTO dto, Pageable pageable);

    CommonResponse<?> delete(Long id);
}
