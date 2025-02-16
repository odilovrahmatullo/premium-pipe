package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.LanguageEntity;
import premium_pipe.repository.ProductRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductSlugService {
    private final LanguageService languageService;
    private final SlugService slugService;
    private final ProductRepository productRepository;


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
        Boolean slugExist = productRepository.existsBySlug(slug);
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
        Boolean slugExists = productRepository.existsBySlugAndIdNot(slug, id);

        if (slugExists) {
            return generateSlug(value, attempt + 1);
        }

        return slug;
    }
}
