package premium_pipe.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import premium_pipe.model.response.AboutResponse;
import premium_pipe.service.admin.AboutService;

@RestController
@RequestMapping("/api/about")
@RequiredArgsConstructor
public class AboutController {
    private final AboutService aboutService;

    @GetMapping
    public ResponseEntity<AboutResponse> home(HttpServletRequest request){
       return ResponseEntity.ok(aboutService.getAbout(request));
    }
}
