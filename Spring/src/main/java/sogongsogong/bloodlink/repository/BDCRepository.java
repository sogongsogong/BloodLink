package sogongsogong.bloodlink.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import sogongsogong.bloodlink.model.BDC;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "bdc", path = "bdc")
public interface BDCRepository extends CrudRepository<BDC, Integer> {

    BDC findByNumber(String number);
    boolean existsByNumber(String number);

    List<BDC> findByOwner(String owner);
    List<BDC> findByDest(String usage);
    boolean existsByOwner(String owner);

    List<BDC> findByUsage(String usage);
    boolean existsByUsage(String usage);
}
