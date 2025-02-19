package premium_pipe.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import premium_pipe.model.response.GalleryListResponse;
import premium_pipe.service.GalleryService;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
public class GalleryController {
    private final GalleryService galleryService;

    @GetMapping
    public ResponseEntity<GalleryListResponse> getList(){
        return ResponseEntity.ok(galleryService.getList());
    }
}
