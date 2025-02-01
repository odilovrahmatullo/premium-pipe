package premium_pipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import premium_pipe.entity.GalleryEntity;

import java.util.Optional;

public interface GalleryRepository extends CrudRepository<GalleryEntity,Long> {
    @Query("SELECT g FROM GalleryEntity g where (lower(g.fileType) like lower(concat('%',:search,'%')))  " +
            "and g.deletedDate is null ")
    Page<GalleryEntity> getGalleries(Pageable pageable,String search);

    @Query("FROM GalleryEntity where id = ?1 and deletedDate is null")
    Optional<GalleryEntity> getGallery(Long id);
}
