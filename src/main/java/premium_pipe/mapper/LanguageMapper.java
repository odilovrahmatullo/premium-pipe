package premium_pipe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import premium_pipe.entity.LanguageEntity;
import premium_pipe.model.request.LanguageRequest;
import premium_pipe.model.response.LanguageResponse;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LanguageMapper {
    LanguageMapper INSTANCE = Mappers.getMapper(LanguageMapper.class);
    LanguageResponse entityToResponse(final LanguageEntity language);
    LanguageEntity responseToEntity(final LanguageRequest request);
    void updateEntity(final LanguageRequest request, @MappingTarget final LanguageEntity language);

}
