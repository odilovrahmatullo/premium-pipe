package premium_pipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import premium_pipe.entity.HomeEntity;

public interface HomeRepository extends CrudRepository<HomeEntity,Long> {

    @Query("FROM HomeEntity where lower(title) like lower(concat('%',:search,'%'))")
    Page<HomeEntity> getAll(Pageable pagination,@Param("search") String search);
}
