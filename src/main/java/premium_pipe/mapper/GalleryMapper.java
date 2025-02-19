package premium_pipe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Value;
import premium_pipe.entity.GalleryEntity;
import premium_pipe.model.response.GalleryResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public abstract class GalleryMapper{
    @Value("${upload.url}")
    String domainName;

    @Mapping(target = "image",expression = "java(mapImage(entity.getImage()))")
    public abstract GalleryResponse toDTO(GalleryEntity entity);

    public String mapImage(String imagePath){
        if(imagePath==null || imagePath.isEmpty()){
            return imagePath;
        }
        return domainName+imagePath;
    }
}
