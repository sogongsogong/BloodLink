package sogongsogong.bloodlink.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sogongsogong.bloodlink.model.Donor;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RepositoryRestResource(collectionResourceRel = "donor", path = "donor")
//public interface DonorRepository extends UserRepository<Donor> {
public interface DonorRepository extends CrudRepository<Donor, Integer> {

    boolean existsByAccount(String account);
    Donor findByAccount(String account);
    List<Donor> findByName(String value);
    List<Donor> findByPhone(String value);
}
