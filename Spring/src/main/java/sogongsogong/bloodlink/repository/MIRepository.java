package sogongsogong.bloodlink.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sogongsogong.bloodlink.model.MI;

@RepositoryRestResource(collectionResourceRel = "mi", path = "mi")
public interface MIRepository extends UserRepository {

    MI findByAccount(String account);
}
