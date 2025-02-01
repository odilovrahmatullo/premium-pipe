package premium_pipe.service.admin;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.CategoryEntity;
import premium_pipe.entity.ProductEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.ProductMapper;
import premium_pipe.model.request.ProductAdminRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.admin.ProductAdminResponse;
import premium_pipe.repository.ProductRepository;
import premium_pipe.service.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAdminService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductFileService productFileService;
    private final CategoryAdminService categoryAdminService;
    private final ProductMapper productMapper;
    private final FileSessionService fileSessionService;
    private final FileDeleteService fileDeleteService;

    public void createProduct(final ProductAdminRequest product, HttpSession session) {
        String dropzoneKey = ProductEntity.class.getName();
        List<String> imagesPath = fileSessionService.getImages(dropzoneKey, session);
        CategoryEntity category = categoryService.getCategoryEntity(product.getCategoryId());
        ProductEntity productEntity = productMapper.toEntity(product);
        productEntity.setCategory(category);
        productRepository.save(productEntity);
        productFileService.create(productEntity, imagesPath);
        fileSessionService.deleteFilesFromSession(dropzoneKey,session);
    }

    public Page<ProductAdminResponse> getProducts(RequestParams params) {
        Pageable pageable = PageRequest.of(params.page(), params.size());
        Page<ProductEntity> products = productRepository.getProducts(params.search(), pageable);
        return products.map(this::getByEntity);
    }

    public Page<ProductAdminResponse> getProductsByCategory(CategoryEntity category, RequestParams params) {
        Pageable pageable = PageRequest.of(params.page(), params.size());
        Page<ProductEntity> products = productRepository.getProductsByCategory(params.search(), pageable, category);
        return products.map(this::getByEntity);
    }

    public ProductAdminResponse getByEntity(ProductEntity entity) {
        return ProductAdminResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .images(productFileService.getProductFiles(entity))
                .category(categoryAdminService.getOne(entity.getCategory().getId()))
                .createdDate(entity.getCreatedDate())
                .build();
    }

    public ProductEntity getProductEntity(Long id) {
        return productRepository.getById(id).orElseThrow(
                () -> new NotFoundException("Product not found"));
    }

    public void delete(Long id) {
        ProductEntity entity = getProductEntity(id);
        productRepository.delete(entity);
    }

    public void update(final ProductEntity product, final ProductAdminRequest par, final HttpSession session) {
        String dropzoneKey = ProductEntity.class.getName();
        List<String> images = fileSessionService.getImages(dropzoneKey,session);
        CategoryEntity category = categoryService.getCategoryEntity(product.getCategory().getId());
        ProductEntity updatedProduct = productMapper.update(par,product);
        updatedProduct.setCategory(category);
        productRepository.save(updatedProduct);
        productFileService.create(updatedProduct,images);
        fileSessionService.deleteFilesFromSession(dropzoneKey,session);
    }

    public void deleteImage(Long id) {
        ProductEntity product = getProductEntity(id);
        List<String> deletedImages = productFileService.getProductFiles(product);
        for(String deletedImage : deletedImages){
        fileDeleteService.deleteFile(deletedImage);
        }
        productFileService.create(product,deletedImages);

    }
}

