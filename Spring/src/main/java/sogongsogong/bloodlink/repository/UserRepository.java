package sogongsogong.bloodlink.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import sogongsogong.bloodlink.model.User;

import java.util.List;

@NoRepositoryBean
public interface UserRepository<T extends User> extends CrudRepository<T, Integer>{

    boolean existsByAccount(String account);
    /*T findByAccount(String account);
    T findByName(String name);
    T findByPhone(String phone);*/
    List<T> findByAccount(String account);
    List<T> findByName(String name);
    List<T> findByPhone(String phone);
}
