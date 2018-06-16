package sogongsogong.bloodlink.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sogongsogong.bloodlink.model.BDC;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "bdc", path = "bdc")
public interface BDCRepository extends CrudRepository<BDC, Integer> {

    BDC findByNumber(String number);
    boolean existsByNumber(String number);

    List<BDC> findByOwner(String owner);
    boolean existsByOwner(String owner);

    List<BDC> findByUsage(String usage);
    boolean existsByUsage(String usage);
}
