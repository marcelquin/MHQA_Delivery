package APP.Repository;

import APP.Entity.ContatoEntity;
import APP.Entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContatoRepository extends JpaRepository<ContatoEntity, Long> {
}
