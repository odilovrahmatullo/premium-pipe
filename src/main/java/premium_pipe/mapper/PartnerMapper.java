package premium_pipe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import premium_pipe.entity.PartnerEntity;
import premium_pipe.model.response.PartnerResponse;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE,unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PartnerMapper {
    PartnerResponse toDTO(PartnerEntity partner);
}
