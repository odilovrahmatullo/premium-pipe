package premium_pipe.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import premium_pipe.entity.FileEntity;

import java.util.Optional;

public interface FileRepository extends CrudRepository<FileEntity,String> {

    @Query("FROM FileEntity where id = :file and deletedDate is null")
    Optional<FileEntity> getById(@Param("file") String file);
}
