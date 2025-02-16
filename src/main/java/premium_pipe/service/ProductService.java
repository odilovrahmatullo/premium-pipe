package premium_pipe.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.CategoryEntity;
import premium_pipe.entity.ProductEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.LocalizeMapper;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.ProductResponse;
import premium_pipe.repository.ProductRepository;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final LocalizeMapper localizeMapper;
    private final ProductInfoService productInfoService;
    private final ProductFileService productFileService;

    public Page<ProductResponse> productsList(@Valid RequestParams params, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(params.page(), params.size());
        Page<ProductEntity> productEntities = productRepository.getProducts(params.search(), pageable);

        return productEntities.map(
                p -> getByEntity(p, request)
        );
    }

    public ProductResponse getByEntity(ProductEntity product, HttpServletRequest request) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(localizeMapper.translate(product.getName(), request))
                .description(localizeMapper.translate(product.getDescription(), request))
                .categoryId(product.getCategory().getId())
                .createdDate(product.getCreatedDate())
                .productInfos(productInfoService.infoResponses(product))
                .images(productFileService.getProductFiles(product))
                .slug(product.getSlug())
                .build();
    }

    public List<ProductResponse> productsExceptThat(final ProductEntity product,final HttpServletRequest request){
            List<ProductEntity> exceptProducts = productRepository.getProductsExceptThat(product.getId());
            return exceptProducts.stream().map(
                    p -> getByEntity(p,request)
            ).toList();
    }

    public ProductEntity getProductById(final Long id){
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }


    public ProductEntity getProductBySlug(String slug) {
        return productRepository
                .findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    public ProductResponse getBySlug(String slug, HttpServletRequest request) {
        ProductEntity product = getProductBySlug(slug);
        ProductResponse responseProduct = getByEntity(product, request);
        responseProduct.setOtherProducts(productsExceptThat(product,request));
        return responseProduct;
    }


    public Page<ProductResponse> getByCategory(CategoryEntity category, Pageable pageable, HttpServletRequest request) {
        Page<ProductEntity> products = productRepository.getByCategory(category, pageable);
        return products.map(
                p -> getByEntity(p, request)
        );
    }
}
