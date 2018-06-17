package sogongsogong.bloodlink.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "donor", path = "donor")
public interface DonorRepository extends UserRepository {

}
