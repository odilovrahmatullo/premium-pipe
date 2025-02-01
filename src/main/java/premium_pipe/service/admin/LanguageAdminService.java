package premium_pipe.service.admin;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.LanguageEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.LanguageMapper;
import premium_pipe.model.request.LanguageRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.LanguageResponse;
import premium_pipe.repository.LanguageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageAdminService {
    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    @Transactional
    public void create(LanguageRequest languageRequest) {
        if (languageRequest.getIsDefault()) {
            makeDefaults();
        }
        LanguageEntity languageEntity = languageMapper.responseToEntity(languageRequest);
        languageRepository.save(languageEntity);
    }

    public void makeDefaults() {
        List<LanguageEntity> defaults = languageRepository.getDefaults();
        for (LanguageEntity lang : defaults) {
            lang.setIsDefault(Boolean.FALSE);
        }
        languageRepository.saveAll(defaults);
    }

    public LanguageResponse getLang(Long id) {
        LanguageEntity entity = getLangEntity(id);
        return languageMapper.entityToResponse(entity);
    }

    public LanguageEntity getLangEntity(Long id){
        return languageRepository.getLang(id).orElseThrow(
                () -> new NotFoundException("Language not found"));
    }

    public void update(Long id, @Valid LanguageRequest request) {
        LanguageEntity entity = getLangEntity(id);
        if(request.getIsDefault() && !entity.getIsDefault()){
            makeDefaults();
        }
        LanguageMapper.INSTANCE.updateEntity(request,entity);
        languageRepository.save(entity);
    }

    public Page<LanguageResponse> getLanguages(RequestParams params) {
        Pageable pageable = PageRequest.of(params.page(), params.size());
        Page<LanguageEntity> languages = languageRepository.getLangs(pageable,params.search());
        return languages.map(languageMapper::entityToResponse);
    }
}
