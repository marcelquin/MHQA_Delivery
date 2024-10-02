package APP.Repositoty;

import APP.Entity.IngredienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteRepository extends JpaRepository<IngredienteEntity,Long> {
}
