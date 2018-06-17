package sogongsogong.bloodlink.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import sogongsogong.bloodlink.model.BDC;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "bdc", path = "bdc")
public interface BDCRepository extends CrudRepository<BDC, Integer> {

    boolean existsByNumber(String number);
    BDC findByNumber(String number);
    List<BDC> findByOwner(String owner);
<<<<<<< HEAD
=======
    List<BDC> findByDest(String usage);
    boolean existsByOwner(String owner);

>>>>>>> 8b91132919c8c3b999049676fed244296fddd05e
    List<BDC> findByUsage(String usage);
}
