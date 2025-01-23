package premium_pipe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import premium_pipe.entity.GalleryEntity;
import premium_pipe.model.response.GalleryResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface GalleryMapper{
    GalleryResponse toDTO(GalleryEntity entity);
}
