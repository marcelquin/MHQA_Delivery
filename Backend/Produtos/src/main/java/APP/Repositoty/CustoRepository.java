package APP.Repositoty;

import APP.Entity.CustoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustoRepository extends JpaRepository<CustoEntity,Long> {


    Optional<CustoEntity> findBynome(String nome);
}
