package premium_pipe.mapper;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import premium_pipe.entity.LanguageEntity;
import premium_pipe.service.LanguageService;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LocalizeMapper {
    private final LanguageService languageService;
    public String translate(final Map<String, String> value, final HttpServletRequest request) {
        if (value == null) return null;

        String language = request.getHeader("Language");

        LanguageEntity defaultLanguage = languageService.findDefault();

        if (language == null || language.isBlank()) {

            if (defaultLanguage == null) {
                return "";
            }

            language = defaultLanguage.getCode();
        }

        String defaultOutput = value.getOrDefault(defaultLanguage.getCode(), "");

        String output = value.getOrDefault(language, defaultOutput);

        return Objects.equals(output, "") ? defaultOutput : output;
    }
}