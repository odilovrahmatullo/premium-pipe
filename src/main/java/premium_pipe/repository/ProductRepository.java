package premium_pipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import premium_pipe.entity.ProductEntity;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<ProductEntity,Long> {

    @Query("FROM ProductEntity p where (lower(p.name) like lower(concat('%',:search,'%') ) ) and p.deletedDate is null")
    Page<ProductEntity> getProducts(String search,Pageable pageable);

    @Query("FROM ProductEntity where id = ?1 and deletedDate is null")
    Optional<ProductEntity> getById(Long id);
}
