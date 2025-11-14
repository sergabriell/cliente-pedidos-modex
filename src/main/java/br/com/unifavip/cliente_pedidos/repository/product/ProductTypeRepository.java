package br.com.unifavip.cliente_pedidos.repository.product;

import br.com.unifavip.cliente_pedidos.models.product.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long>, JpaSpecificationExecutor<ProductType> {
    Optional<ProductType> findByTypeKeyIgnoreCase(String typeKey);
}
