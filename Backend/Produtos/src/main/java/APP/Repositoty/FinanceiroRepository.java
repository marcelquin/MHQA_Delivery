package APP.Repositoty;

import APP.Entity.FinanceiroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceiroRepository extends JpaRepository<FinanceiroEntity,Long> {
}
