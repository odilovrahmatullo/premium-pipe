package premium_pipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import premium_pipe.entity.NewsEntity;

import java.util.Optional;


public interface NewsRepository extends CrudRepository<NewsEntity,Long> {
    @Query("FROM NewsEntity where deletedDate is null")
    Page<NewsEntity> getNewsPages(Pageable pageable);

    @Query("FROM NewsEntity where id = ?1")
    Optional<NewsEntity> getNews(Long id);
}
