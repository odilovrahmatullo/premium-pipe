package premium_pipe.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import premium_pipe.model.response.BannerResponse;
import premium_pipe.service.BannerService;

@RestController
@RequestMapping("/api/banner")
@RequiredArgsConstructor
public class BannerController {
    private final BannerService bannerService;

    @GetMapping()
    public ResponseEntity<BannerResponse> getBanner(HttpServletRequest request){
        return ResponseEntity.ok(bannerService.getOne(request));
    }

}
