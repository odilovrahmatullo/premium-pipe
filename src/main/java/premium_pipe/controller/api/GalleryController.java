package premium_pipe.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.GalleryResponse;
import premium_pipe.service.GalleryService;

@RestController
@RequestMapping("/api/gallery")
@RequiredArgsConstructor
public class GalleryController {
    private final GalleryService galleryService;

    @GetMapping
    public ResponseEntity<Page<GalleryResponse>> galleryList(@Valid RequestParams params){
        try{
           return ResponseEntity.ok(galleryService.getGalleries(params));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
