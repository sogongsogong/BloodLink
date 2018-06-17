package sogongsogong.bloodlink.repository;

import org.springframework.data.repository.CrudRepository;
import sogongsogong.bloodlink.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository {

    boolean existsByAccount(String account);
    List findByAccount(String account);
    List findByName(String name);
    List findByPhone(String phone);
}
