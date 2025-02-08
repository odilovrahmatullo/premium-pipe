package premium_pipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import premium_pipe.entity.PartnerEntity;

public interface PartnerRepository extends CrudRepository<PartnerEntity,Long> {
    @Query("FROM PartnerEntity order by createdDate desc ")
    Page<PartnerEntity> getList(Pageable pageable);
}
