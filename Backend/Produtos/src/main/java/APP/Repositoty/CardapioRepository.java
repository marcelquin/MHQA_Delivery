package APP.Repositoty;

import APP.Entity.CardapioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardapioRepository extends JpaRepository<CardapioEntity,Long> {

    Optional<CardapioEntity> findBycodigo(String codigo);
}
