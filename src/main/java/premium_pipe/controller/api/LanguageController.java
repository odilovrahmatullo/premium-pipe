package premium_pipe.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import premium_pipe.model.response.LanguageResponse;
import premium_pipe.service.LanguageService;

import java.util.List;

@RestController
@RequestMapping("/api/language")
@RequiredArgsConstructor
public class LanguageController {
    private final LanguageService languageService;

    @GetMapping({"","/"})
    public ResponseEntity<List<LanguageResponse>> languages(){
        List<LanguageResponse> languageResponses = languageService.getActives();
        return ResponseEntity.ok(languageResponses);
    }
}
