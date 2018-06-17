package sogongsogong.bloodlink.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sogongsogong.bloodlink.model.MI;

import javax.transaction.Transactional;

@Transactional
@RepositoryRestResource(collectionResourceRel = "mi", path = "mi")
public interface MIRepository extends UserRepository<MI> {
    //MI findByAccount(String account);
}

/*MI USER BDC MI*/
