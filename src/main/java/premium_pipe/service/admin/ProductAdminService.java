package premium_pipe.service.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.CategoryEntity;
import premium_pipe.entity.ProductEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.model.request.ProductAdminRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.admin.ProductAdminResponse;
import premium_pipe.repository.ProductRepository;
import premium_pipe.service.CategoryService;
import premium_pipe.service.ProductFileService;
import premium_pipe.service.ProductInfoService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductAdminService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductFileService productFileService;
    private final ProductInfoService productInfoService;
    private final CategoryAdminService categoryAdminService;

    public void createProduct(final ProductAdminRequest product){
        CategoryEntity category = categoryService.getCategoryEntity(product.getCategoryId());
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(product.getName());
        productEntity.setCategory(category);
        productEntity.setDescription(product.getDescription());
        productRepository.save(productEntity);
        productFileService.create(productEntity,product.getFileIds());
        productInfoService.create(productEntity,product.getProductInfos());
    }

    public Page<ProductAdminResponse> getProducts(RequestParams params) {
        Pageable pageable = PageRequest.of(params.page(),params.size());
        Page<ProductEntity> products = productRepository.getProducts(params.search(),pageable);
        return products.map(this::getByEntity);
    }

    public ProductAdminResponse getByEntity(ProductEntity entity){
        return ProductAdminResponse.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .productInfos(productInfoService.infoResponses(entity))
                .fileIds(productFileService.getProductFiles(entity))
                .category(categoryAdminService.getOne(entity.getCategory().getId()))
                .build();
    }
    public ProductEntity getProductEntity(Long id){
        return productRepository.getById(id).orElseThrow(
                () -> new NotFoundException("Product not found"));
    }

    public void update(Long id, ProductAdminRequest par) {
        ProductEntity product = getProductEntity(id);
        product.setName(par.getName());
        product.setDescription(par.getDescription());
        product.setCategory(categoryService.getCategoryEntity(par.getCategoryId()));
        productRepository.save(product);
        productFileService.create(product,par.getFileIds());
        productInfoService.create(product,par.getProductInfos());
    }

    public void delete(Long id) {
        ProductEntity entity = getProductEntity(id);
        entity.setDeletedDate(LocalDateTime.now());
        productRepository.save(entity);
    }
}

