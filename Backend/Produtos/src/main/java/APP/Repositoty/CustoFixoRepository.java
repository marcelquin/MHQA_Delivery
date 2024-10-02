package APP.Repositoty;

import APP.Entity.CustoFixoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustoFixoRepository extends JpaRepository<CustoFixoEntity,Long> {
}
