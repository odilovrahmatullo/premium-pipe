package premium_pipe.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import premium_pipe.entity.LanguageEntity;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository extends CrudRepository<LanguageEntity,Long> {
    @Query("SELECT l FROM LanguageEntity l WHERE l.isActive = true ORDER BY l.isDefault DESC")
    List<LanguageEntity> getActives();

    @Query(value = "SELECT * FROM language_entity WHERE is_default = 1 LIMIT 1", nativeQuery = true)
    Optional<LanguageEntity> findDefault();

    @Query("FROM LanguageEntity where isDefault = true")
    List<LanguageEntity> getDefaults();

    @Query("FROM LanguageEntity where id = ?1")
    Optional<LanguageEntity> getLang(Long id);
}
