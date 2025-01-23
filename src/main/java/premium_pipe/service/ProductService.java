package premium_pipe.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.ProductEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.LocalizeMapper;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.ProductResponse;
import premium_pipe.repository.ProductRepository;


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
                p -> getById(p.getId(),request)
        );
    }

    public ProductResponse getById(Long id, HttpServletRequest request) {
        ProductEntity entity = getProductEntity(id);
        return ProductResponse.builder()
                .name(localizeMapper.translate(entity.getName(),request))
                .description(localizeMapper.translate(entity.getDescription(), request))
                .categoryId(entity.getCategory().getId())
                .createdDate(entity.getCreatedDate())
                .productInfos(productInfoService.infoResponses(entity))
                .fileIds(productFileService.getProductFiles(entity))
                .build();
    }

    public ProductEntity getProductEntity(Long id){
        return productRepository
                .getById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }
}
