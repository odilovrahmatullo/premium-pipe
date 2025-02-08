package premium_pipe.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import premium_pipe.entity.ProductEntity;
import premium_pipe.entity.ProductFileEntity;

import java.util.List;

public interface ProductFileRepository extends CrudRepository<ProductFileEntity,Long> {
    @Query("FROM ProductFileEntity where product = ?1 order by createdDate desc ")
    List<ProductFileEntity> getProductFIle(ProductEntity entity);
}
