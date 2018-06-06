package sogongsogong.bloodlink.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sogongsogong.bloodlink.model.Donor;

@RepositoryRestResource(collectionResourceRel = "donor", path = "donor")
public interface DonorRepository extends UserRepository {

    Donor findByAccount(String account);
}
