package APP.Repository;

import APP.Entity.ItemLogisticaEntity;
import APP.Entity.LogisticaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemLogisticaRepository extends JpaRepository<ItemLogisticaEntity, Long> {
}
