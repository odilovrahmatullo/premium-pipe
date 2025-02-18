package premium_pipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import premium_pipe.entity.StaticTypeEntity;

public interface StaticTypeRepository extends CrudRepository<StaticTypeEntity,Long> {
    Page<StaticTypeEntity> findAll(Pageable pageable);
}
