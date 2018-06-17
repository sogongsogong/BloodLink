package sogongsogong.bloodlink.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

<<<<<<< HEAD
=======
@Transactional
@RepositoryRestResource(collectionResourceRel = "mi", path = "mi")
public interface MIRepository extends UserRepository<MI> {
    //MI findByAccount(String account);
>>>>>>> 8b91132919c8c3b999049676fed244296fddd05e
}

/*MI USER BDC MI*/
