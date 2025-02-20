package premium_pipe.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.CategoryEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.LocalizeMapper;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.CategoryApiDetailsResponse;
import premium_pipe.model.response.CategoryApiResponse;
import premium_pipe.model.response.ProductResponse;
import premium_pipe.repository.CategoryRepository;
import premium_pipe.repository.ProductRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final LocalizeMapper localizeMapper;
    private final ProductService productService;

    @Value("${upload.url}")
    private String domainName;

    public Page<CategoryApiResponse> getList(RequestParams params, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(params.page(),params.size());
        Page<CategoryEntity> categoryEntities =categoryRepository.search(params.search(),pageable);
        return categoryEntities.map(
                c -> getByEntity(c,request));
    }

    public CategoryEntity getCategoryEntity(final Long categoryId) {
        return categoryRepository.getCategory(categoryId)
                .orElseThrow(() -> new NotFoundException("Category Not Found"));
    }

    public CategoryApiResponse getByEntity(CategoryEntity c,HttpServletRequest request){
        return CategoryApiResponse
                .builder()
                .id(c.getId())
                .name(localizeMapper.translate(c.getName(),request))
                .description(localizeMapper.translate(c.getDescription(),request))
                .image(domainName+c.getImage())
                .slug(c.getSlug())
                .build();
    }

    public CategoryApiResponse getBySlug(String slug,HttpServletRequest request){
        CategoryEntity category = categoryRepository.findBySlug(slug)
                .orElseThrow(()-> new NotFoundException("Category Not Found"));
        return getByEntity(category,request);
    }

    public CategoryApiDetailsResponse getCategoriesWithProducts(String slug, HttpServletRequest request) {
        CategoryEntity category = categoryRepository.findBySlug(slug).orElseThrow(()-> new NotFoundException("Category not found"));
        List<CategoryEntity> categories = categoryRepository.getList();
        CategoryApiDetailsResponse apiDetailsResponse = getCategoryDetail(category,request);
        List<CategoryApiDetailsResponse> allCategories = categories.stream().map(one -> getCategoryDetail(one,request)).toList();
        apiDetailsResponse.setAllCategories(allCategories);
        return apiDetailsResponse;
    }

    public CategoryApiDetailsResponse getCategoryDetail(CategoryEntity category,HttpServletRequest request){
        List<ProductResponse> products = productService.getByCategory(category,request);
        return CategoryApiDetailsResponse.builder()
                .id(category.getId())
                .name(localizeMapper.translate(category.getName(),request))
                .image(domainName+category.getImage())
                .slug(category.getSlug())
                .description(localizeMapper.translate(category.getDescription(),request))
                .products(products)
                .build();
    }
}
