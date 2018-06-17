package sogongsogong.bloodlink.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import sogongsogong.bloodlink.model.User;

import javax.transaction.Transactional;
@NoRepositoryBean
public interface UserRepository<T extends User> extends CrudRepository<T, Integer>{

    T findByAccount(String account);
    boolean existsByAccount(String account);
}
