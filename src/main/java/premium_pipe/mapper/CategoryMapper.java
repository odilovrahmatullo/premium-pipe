package premium_pipe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import premium_pipe.entity.CategoryEntity;
import premium_pipe.model.response.admin.CategoryAdminResponse;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    CategoryAdminResponse toDTO (CategoryEntity entity);
}
