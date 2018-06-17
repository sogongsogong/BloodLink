package sogongsogong.bloodlink.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sogongsogong.bloodlink.model.Donor;

import javax.transaction.Transactional;

@Transactional
@RepositoryRestResource(collectionResourceRel = "donor", path = "donor")
public interface DonorRepository extends UserRepository<Donor> {

   // Donor findByAccount(String account);
}
