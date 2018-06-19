package sogongsogong.bloodlink.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sogongsogong.bloodlink.model.MI;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RepositoryRestResource(collectionResourceRel = "mi", path = "mi")
//public interface MIRepository extends UserRepository<MI> {
public interface MIRepository extends CrudRepository<MI, Integer> {

    boolean existsByAccount(String account);
    MI findByAccount(String account);
    List<MI> findByName(String value);
    List<MI> findByPhone(String value);
}

/*MI USER BDC MI*/
