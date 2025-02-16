package premium_pipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import premium_pipe.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity,Long> {

    @Query("FROM CategoryEntity c where c.deletedDate is null and (lower(c.name) like lower(concat('%', :search, '%') )) order by c.createdDate desc ")
    Page<CategoryEntity> search(@Param("search") String search, Pageable pageable);

    @Query("FROM CategoryEntity where id = ?1 and deletedDate is null")
    Optional<CategoryEntity> getCategory(Long id);

    @Query("FROM CategoryEntity where deletedDate is null")
    List<CategoryEntity> getList();

    Boolean existsBySlug(String slug);


    Boolean existsBySlugAndIdNot(String slug, Long id);

    Optional<CategoryEntity> findBySlug(String slug);

}
