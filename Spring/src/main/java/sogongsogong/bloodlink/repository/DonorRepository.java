package sogongsogong.bloodlink.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@Transactional
@RepositoryRestResource(collectionResourceRel = "donor", path = "donor")
public interface DonorRepository extends UserRepository<Donor> {

<<<<<<< HEAD
=======
   // Donor findByAccount(String account);
>>>>>>> 8b91132919c8c3b999049676fed244296fddd05e
}
