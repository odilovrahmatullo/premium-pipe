package premium_pipe.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import premium_pipe.entity.ProductEntity;
import premium_pipe.entity.ProductInfoEntity;

import java.util.List;

public interface ProductInfoRepository extends CrudRepository<ProductInfoEntity,Long> {
    @Query("FROM ProductInfoEntity where product = ?1 and deletedDate is null")
    List<ProductInfoEntity> getProductsInfos(ProductEntity product);
}
