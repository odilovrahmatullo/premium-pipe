package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.LanguageEntity;
import premium_pipe.repository.CategoryRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategorySlugService {
    private final SlugService slugService;
    private final LanguageService languageService;
    private final CategoryRepository categoryRepository;

    public String generateSlug(final Map<String, String> value) {
        LanguageEntity defaultLang = languageService.findDefault();
        String stringValue = value.get(defaultLang.getCode());
        return generateSlug(stringValue, 0);
    }
    public String generateSlug(final String value, final Integer attempt) {
        String slug = slugService.generateSlug(value);
        if (attempt > 0) {
            slug += String.format("-%d", attempt);
        }
        Boolean slugExist = categoryRepository.existsBySlug(slug);
        if (slugExist) {
            return generateSlug(value, attempt + 1);
        }
        return slug;
    }

    public String generateSlug(final Long id, final Map<String, String> value) {
        LanguageEntity defaultLang = languageService.findDefault();

        String stringValue = value.get(defaultLang.getCode());

        return generateSlug(id, stringValue, 0);
    }

    public String generateSlug(final Long id, final String value, final Integer attempt) {
        String slug = slugService.generateSlug(value);

        if (attempt > 0) {
            slug += String.format("-%d", attempt);
        }

        Boolean slugExists = categoryRepository.existsBySlugAndIdNot(slug, id);

        if (slugExists) {
            return generateSlug(value, attempt + 1);
        }

        return slug;
    }
}
