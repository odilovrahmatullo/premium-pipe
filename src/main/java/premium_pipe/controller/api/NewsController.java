package premium_pipe.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.NewsResponse;
import premium_pipe.service.NewsService;

@RequestMapping("/api/news")
@RestController
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<Page<NewsResponse>> list(@Valid final RequestParams params, final HttpServletRequest request){
        try {
           return ResponseEntity.ok(newsService.getNews(params, request));

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/{slug}")
    public ResponseEntity<NewsResponse> one(@PathVariable("slug") final String slug,
                                            final HttpServletRequest request){
        try{
            return ResponseEntity.ok(newsService.getNewsBySlug(slug,request));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
