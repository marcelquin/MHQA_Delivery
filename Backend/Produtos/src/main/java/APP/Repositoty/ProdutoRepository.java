package APP.Repositoty;

import APP.Entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity,Long> {

    Optional<ProdutoEntity> findBycodigo(String codigo);
}
