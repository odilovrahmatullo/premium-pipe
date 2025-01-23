package premium_pipe.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import premium_pipe.entity.AdminEntity;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<AdminEntity,Long> {
    @Query("FROM AdminEntity where username = ?1 and isActive = true")
    Optional<AdminEntity> getByUsername(String username);
}
