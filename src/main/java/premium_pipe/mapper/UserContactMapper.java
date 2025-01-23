package premium_pipe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import premium_pipe.entity.UserContactEntity;
import premium_pipe.model.response.UserContactResponse;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserContactMapper {

    UserContactResponse toDTO(UserContactEntity entity);
}
