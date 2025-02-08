package premium_pipe.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.ProductEntity;
import premium_pipe.entity.ProductInfoEntity;
import premium_pipe.mapper.ProductInfoMapper;
import premium_pipe.model.request.ProductInfoRequest;
import premium_pipe.repository.ProductInfoRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductInfoAdminService {
    private final ProductInfoRepository productInfoRepository;
    private final ProductInfoMapper infoMapper;

    public void create(ProductEntity productEntity, List<ProductInfoRequest> productDetails) {
        for (ProductInfoRequest info : productDetails) {
            if (info != null){
                ProductInfoEntity productInfo = infoMapper.toEntity(info);
                productInfo.setProduct(productEntity);
                productInfoRepository.save(productInfo);
            }
        }
    }

}
