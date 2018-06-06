package sogongsogong.bloodlink.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sogongsogong.bloodlink.model.Bulletin;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "bulletin", path = "bulletin")
public interface BulletinRepository extends CrudRepository<Bulletin, Integer> {

    List<Bulletin> findByTitle(String title);
    List<Bulletin> findByAuthor(String author);
}
