package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.LanguageEntity;
import premium_pipe.mapper.LanguageMapper;
import premium_pipe.model.response.LanguageResponse;
import premium_pipe.repository.LanguageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;

    public List<LanguageResponse> getActives() {
        List<LanguageEntity> languageEntities = languageRepository.getActives();
        return languageEntities
                .stream()
                .map(LanguageMapper.INSTANCE::entityToResponse)
                .toList();
    }
    public LanguageEntity findDefault() {
        return languageRepository.findDefault().orElse(null);
    }
}
