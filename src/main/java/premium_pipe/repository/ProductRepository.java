package premium_pipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import premium_pipe.entity.CategoryEntity;
import premium_pipe.entity.ProductEntity;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<ProductEntity,Long> {

    @Query("FROM ProductEntity p where (lower(p.name) like lower(concat('%',:search,'%') ) ) order by p.id desc ")
    Page<ProductEntity> getProducts(String search,Pageable pageable);

    @Query("FROM ProductEntity where id = ?1")
    Optional<ProductEntity> getById(Long id);

    @Query("FROM ProductEntity p where (lower(p.name) like lower(concat('%',:search,'%') ) ) and p.category = :category order by p.id desc ")
    Page<ProductEntity> getProductsByCategory(@Param("search") String search, Pageable pageable,@Param("category") CategoryEntity category);
}
