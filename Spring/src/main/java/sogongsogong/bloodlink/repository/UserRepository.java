package sogongsogong.bloodlink.repository;

import org.springframework.data.repository.CrudRepository;
import sogongsogong.bloodlink.model.User;

public interface UserRepository extends CrudRepository {

    User findByAccount(String account);
    boolean existsByAccount(String account);
}
