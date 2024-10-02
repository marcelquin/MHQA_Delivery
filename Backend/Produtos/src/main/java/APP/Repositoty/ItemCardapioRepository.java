package APP.Repositoty;

import APP.Entity.ItemCardapioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCardapioRepository extends JpaRepository<ItemCardapioEntity,Long> {
}
