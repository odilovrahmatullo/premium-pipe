package premium_pipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import premium_pipe.entity.UserContactEntity;
import java.util.Optional;

public interface UserContactRepository extends CrudRepository<UserContactEntity,Long> {
    @Query("FROM UserContactEntity uc where (lower(uc.name) like lower(concat('%',:search,'%') ) " +
            "or lower(uc.email) like lower(concat('%',:search,'%') )) and uc.deletedDate IS NULL order by uc.createdDate desc")
    Page<UserContactEntity> getContacts(Pageable pageable, @Param("search") String search);

    @Query("FROM UserContactEntity where id = ?1 and deletedDate is null ")
    Optional<UserContactEntity> getContact(Long id);
}
