package premium_pipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import premium_pipe.entity.BannerEntity;

import java.util.Optional;

public interface BannerRepository extends CrudRepository<BannerEntity,Long> {

    Page<BannerEntity> findAll(Pageable pageable);

    @Query("FROM BannerEntity ")
    Optional<BannerEntity> getBanner();
}
