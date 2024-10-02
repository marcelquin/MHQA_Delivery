package APP.Repository;

import APP.Entity.LogisticaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticaRepository extends JpaRepository<LogisticaEntity, Long> {
}
