package APP.Repository;

import APP.Entity.EstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<EstoqueEntity,Long> {

    Optional<EstoqueEntity> findBynome(String nome);

    Optional<EstoqueEntity> findBycodigo(String codigo);
}
