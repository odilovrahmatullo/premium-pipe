package premium_pipe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import premium_pipe.entity.FileEntity;
import premium_pipe.model.response.FileResponse;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FileMapper {

    FileResponse toDTO (FileEntity entity);
}
