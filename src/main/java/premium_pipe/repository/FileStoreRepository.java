package premium_pipe.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import premium_pipe.entity.FileStoreEntity;
import java.time.LocalDateTime;
import java.util.List;

public interface FileStoreRepository extends CrudRepository<FileStoreEntity,Long> {
    @Query("SELECT f FROM FileStoreEntity f WHERE f.expiredAt <= :expireAt")
    List<FileStoreEntity> findByExpireAt(@Param("expireAt") LocalDateTime expireAt);

    @Transactional
    @Modifying
    @Query("DELETE FROM FileStoreEntity f WHERE f.expiredAt <= :expireAt")
    void deleteByExpiredAt(@Param("expireAt") LocalDateTime expireAt);
}
