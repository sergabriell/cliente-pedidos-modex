package br.com.unifavip.cliente_pedidos.repository.user;

import br.com.unifavip.cliente_pedidos.models.user.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long>, JpaSpecificationExecutor<UserGroup> {
    Optional<UserGroup> findByNameIgnoreCase(String name);
}
