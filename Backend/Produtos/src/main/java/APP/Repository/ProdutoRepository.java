package APP.Repository;


import APP.Entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Properties;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity,Long> {

    Optional<ProdutoEntity> findBynome(String nome);

    Optional<ProdutoEntity> findBycodigo(String codigo);
}
