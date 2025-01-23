package premium_pipe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import premium_pipe.entity.ProductInfoEntity;
import premium_pipe.model.response.ProductInfoResponse;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductInfoMapper {
    ProductInfoResponse toDTO(ProductInfoEntity entity);
}
