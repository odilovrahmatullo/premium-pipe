package premium_pipe.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.StaticTypeEntity;
import premium_pipe.entity.TranslateEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.TranslationMapper;
import premium_pipe.model.response.admin.TranslateAdminRequest;
import premium_pipe.model.response.admin.TranslationAdminResponse;
import premium_pipe.repository.TranslationRepository;

@Service
@RequiredArgsConstructor
public class TranslateAdminService {
    private final TranslationRepository translationRepository;
    private final StaticTypeAdminService staticTypeAdminService;
    private final TranslationMapper translationMapper;

    public void create(TranslateAdminRequest request) {
        TranslateEntity translateEntity = translationMapper.toEntity(request);
        StaticTypeEntity typeEntity = staticTypeAdminService.getEntity(request.getStaticId());
        translateEntity.setType(typeEntity);
        translationRepository.save(translateEntity);
    }

    public Page<TranslationAdminResponse> translationAdminResponses(Pageable pageable,final Long staticId){
        Page<TranslateEntity> pages;
        if(staticId!=null){
            pages = translationRepository.findByStaticType(staticId,pageable);
        }
        else{
            pages = translationRepository.findAll(pageable);
        }
        return pages.map(this::map);
    }

    public TranslationAdminResponse map(final TranslateEntity translateEntity){
        return  TranslationAdminResponse.builder()
                .id(translateEntity.getId())
                .keyword(translateEntity.getKeyword())
                .keyUz(translateEntity.getKeyUz())
                .keyEn(translateEntity.getKeyEn())
                .keyRu(translateEntity.getKeyRu())
                .staticTypeName(translateEntity.getType())
                .build();
    }

    public TranslateEntity getEntity(final Long id){
        return translationRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Translate not found"));
    }

    public void delete(final Long id){
        TranslateEntity translateEntity = getEntity(id);
        translationRepository.delete(translateEntity);
    }

}
