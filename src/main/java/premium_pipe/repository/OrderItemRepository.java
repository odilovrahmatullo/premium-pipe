package premium_pipe.repository;

import org.springframework.data.repository.CrudRepository;
import premium_pipe.entity.CartItemEntity;

public interface OrderItemRepository extends CrudRepository<CartItemEntity,Long> {
}
