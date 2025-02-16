package premium_pipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import premium_pipe.entity.NewsEntity;

import java.util.List;
import java.util.Optional;


public interface NewsRepository extends CrudRepository<NewsEntity,Long> {
    @Query("SELECT n FROM NewsEntity n where lower(n.title) like lower(concat('%',:search,'%')) order by n.createdDate desc ")
    Page<NewsEntity> getNewsPages(Pageable pageable,@Param("search") String search);

    @Query("FROM NewsEntity where id = ?1")
    Optional<NewsEntity> getNews(final Long id);

    Boolean existsBySlug(final String slug);

    Boolean existsBySlugAndIdNot(final String slug,final Long id);

    @Query("FROM NewsEntity where slug = ?1")
    Optional<NewsEntity> getBySlug(final String slug);

    @Query("FROM NewsEntity where id != ?1")
    List<NewsEntity> getNewsExceptThat(final Long id);
}
