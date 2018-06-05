package sogongsogong.bloodlink.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sogongsogong.bloodlink.model.User;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findByEmail(String email);
}
