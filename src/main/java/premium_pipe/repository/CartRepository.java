package premium_pipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import premium_pipe.entity.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity,Long> {

}
