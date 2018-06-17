package sogongsogong.bloodlink.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import sogongsogong.bloodlink.model.User;

<<<<<<< HEAD
import java.util.List;

public interface UserRepository extends CrudRepository {

=======
import javax.transaction.Transactional;
@NoRepositoryBean
public interface UserRepository<T extends User> extends CrudRepository<T, Integer>{

    T findByAccount(String account);
>>>>>>> 8b91132919c8c3b999049676fed244296fddd05e
    boolean existsByAccount(String account);
    List findByAccount(String account);
    List findByName(String name);
    List findByPhone(String phone);
}
