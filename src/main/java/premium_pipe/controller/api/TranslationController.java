package premium_pipe.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import premium_pipe.model.response.TranslateResultResponse;
import premium_pipe.service.TranslationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/translation")
@RequiredArgsConstructor
public class TranslationController {
    private final TranslationService translationService;

    @GetMapping
    public ResponseEntity<Map<String, List<TranslateResultResponse>>> getTranslations(HttpServletRequest request){
       return ResponseEntity.ok(translationService.getTranslations(request));
    }

}
