package premium_pipe.service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.ProductEntity;
import premium_pipe.entity.ProductInfoEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.ProductInfoMapper;
import premium_pipe.model.request.ProductInfoRequest;
import premium_pipe.model.response.ProductInfoResponse;
import premium_pipe.repository.ProductInfoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductInfoService {
    private final ProductInfoRepository productInfoRepository;
    private final ProductInfoMapper infoMapper;

    public List<ProductInfoResponse> infoResponses(ProductEntity product) {
        List<ProductInfoEntity> products = productInfoRepository.getProductsInfos(product);
        return products.stream().map(infoMapper::toDTO).toList();
    }

    public void create(ProductEntity productEntity, List<ProductInfoRequest> productInfos) {
        for (ProductInfoRequest infoRequest : productInfos) {
            ProductInfoEntity pi = ProductInfoEntity.builder()
                    .product(productEntity)
                    .codeValue(infoRequest.getCodeValue())
                    .diameterValue(infoRequest.getDiameterValue())
                    .unitValue(infoRequest.getUnitValue())
                    .packageValue(infoRequest.getPackageValue())
                    .build();
            productInfoRepository.save(pi);
        }
    }

    public ProductInfoEntity getEntity(final Long id){
        return productInfoRepository.findById(id).orElseThrow(()-> new NotFoundException("ProductInfo not found"));
    }
}
