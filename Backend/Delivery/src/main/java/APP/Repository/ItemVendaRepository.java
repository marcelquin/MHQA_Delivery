package APP.Repository;

import APP.Entity.ItemVendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVendaEntity, Long> {
}
