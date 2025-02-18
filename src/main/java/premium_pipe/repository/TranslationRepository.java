package premium_pipe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import premium_pipe.entity.TranslateEntity;

import java.util.List;

public interface TranslationRepository extends CrudRepository<TranslateEntity,Long> {
    Page<TranslateEntity> findAll(Pageable pageable);

    @Query("FROM TranslateEntity where type.id = ?1")
    Page<TranslateEntity> findByStaticType(Long staticId,Pageable pageable);


        @Query(value = """
        SELECT st.name AS staticType,
               JSON_ARRAYAGG(
                   JSON_OBJECT(
                       'key', t.keyword,
                       'value', 
                       CASE 
                           WHEN :lang = 'uz' THEN t.key_uz
                           WHEN :lang = 'ru' THEN t.key_ru
                           WHEN :lang = 'en' THEN t.key_en
                           ELSE t.key_uz
                       END
                   )
               ) AS translations
        FROM translate t
        JOIN static_type st ON t.static_type = st.id
        GROUP BY st.name
        """, nativeQuery = true)
        List<Object[]> getTranslations(@Param("lang") String lang);

}
