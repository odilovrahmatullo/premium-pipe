package premium_pipe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import premium_pipe.entity.TranslateEntity;
import premium_pipe.model.response.admin.TranslateAdminRequest;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TranslationMapper {

    TranslateEntity toEntity(TranslateAdminRequest request);

    TranslateEntity update(TranslateAdminRequest request, @MappingTarget TranslateEntity translate);
}
