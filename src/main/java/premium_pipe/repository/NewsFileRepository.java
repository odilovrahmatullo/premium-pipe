package premium_pipe.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import premium_pipe.entity.FileEntity;
import premium_pipe.entity.NewsEntity;
import premium_pipe.entity.NewsFileEntity;

import java.util.List;

public interface NewsFileRepository extends CrudRepository<NewsFileEntity,Long> {
    @Query("FROM NewsFileEntity where news = ?1 and deletedDate is null ")
    List<FileEntity> getFiles(NewsEntity n);
}
