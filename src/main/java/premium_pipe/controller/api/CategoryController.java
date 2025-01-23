package premium_pipe.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.CategoryApiResponse;
import premium_pipe.service.CategoryService;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    public ResponseEntity<Page<CategoryApiResponse>> list(@Valid RequestParams params, final HttpServletRequest request){
        Page<CategoryApiResponse> categories =categoryService.getList(params,request);
        return ResponseEntity.ok(categories);
    }





}
