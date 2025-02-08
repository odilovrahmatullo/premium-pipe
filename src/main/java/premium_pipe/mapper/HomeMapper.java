package premium_pipe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import premium_pipe.entity.HomeEntity;
import premium_pipe.model.request.HomeRequest;
import premium_pipe.model.response.admin.HomeAdminResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface HomeMapper {
    HomeEntity toEntity(HomeRequest homeRequest);
    HomeAdminResponse toDTO(HomeEntity homeEntity);
    void update(HomeRequest request,@MappingTarget HomeEntity home);
}
