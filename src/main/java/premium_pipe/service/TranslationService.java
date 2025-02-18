package premium_pipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.model.response.TranslateResultResponse;
import premium_pipe.repository.TranslationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final TranslationRepository translationRepository;
    private final LanguageService languageService;
    private final ObjectMapper objectMapper;

    public Map<String, List<TranslateResultResponse>> getTranslations(HttpServletRequest request) {
        String language = request.getHeader("Accept-Language");
        if(language==null || language.isEmpty()){
            language = languageService.findDefault().getCode();
        }
        List<Object[]> results = translationRepository.getTranslations(language);
        Map<String, List<TranslateResultResponse>> translationMap = new HashMap<>();
        for (Object[] row : results) {
            String staticType = (String) row[0];
            String jsonArray = (String) row[1];
            try {
                List<TranslateResultResponse> translations = objectMapper.readValue(jsonArray,
                        new TypeReference<>() {});
                translationMap.put(staticType, translations);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("JSON parsing error", e);
            }
        }
        return translationMap;
    }
}
