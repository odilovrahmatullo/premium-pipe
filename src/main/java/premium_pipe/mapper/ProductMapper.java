package premium_pipe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import premium_pipe.entity.ProductEntity;
import premium_pipe.model.request.ProductAdminRequest;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductEntity toEntity(ProductAdminRequest productAdminRequest);
    ProductEntity update(final ProductAdminRequest par, @MappingTarget final ProductEntity product);
}
