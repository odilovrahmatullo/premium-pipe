package premium_pipe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Value;
import premium_pipe.entity.PartnerEntity;
import premium_pipe.model.response.PartnerResponse;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE,unmappedSourcePolicy = ReportingPolicy.IGNORE)
public abstract class PartnerMapper {

    public abstract PartnerResponse toDTO(PartnerEntity partner);
    @Value("${upload.url}")
    String domainName;

    @Mapping(target = "image",expression = "java(mapImage(entity.getImage()))")
    public abstract PartnerResponse toPartnerResponse(PartnerEntity entity);

    public String mapImage(String imagePath){
        if(imagePath==null || imagePath.isEmpty()){
            return imagePath;
        }
        return domainName+imagePath;
    }
}
