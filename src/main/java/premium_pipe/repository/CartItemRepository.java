package premium_pipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import premium_pipe.entity.CartItemEntity;

public interface CartItemRepository extends JpaRepository<CartItemEntity,Long> {
    void deleteAllByCartId(Long id);
}
