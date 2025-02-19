package premium_pipe.mapper;

import jakarta.servlet.http.HttpServletRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import premium_pipe.entity.BannerEntity;
import premium_pipe.model.request.BannerAdminRequest;
import premium_pipe.model.response.BannerInfo;
import premium_pipe.model.response.BannerResponse;
import premium_pipe.model.response.admin.BannerAdminResponse;

import java.util.Map;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public abstract class BannerMapper {
    @Autowired public LocalizeMapper localizeMapper;
    @Value("${upload.url}")
    String domainName;

    public abstract BannerEntity toEntity (final BannerAdminRequest request);

    public abstract BannerAdminResponse entityToResponse(final BannerEntity entity);

    @Mapping(target = "title", expression = "java(mapJsonValue(entity.getTitle(), request))")
    @Mapping(target = "description", expression = "java(mapJsonValue(entity.getDescription(), request))")
    @Mapping(target = "image",expression = "java(mapImage(entity.getImage()))")
    @Mapping(target = "info", expression = "java(mapToInfo(entity, request))")
    public abstract BannerResponse entityToResponse(final BannerEntity entity, final HttpServletRequest request);

    public String mapJsonValue(final Map<String, String> value, final HttpServletRequest request) {
        return localizeMapper.translate(value, request);
    }

    public BannerInfo mapToInfo(BannerEntity entity, HttpServletRequest request) {
        BannerInfo infos = new BannerInfo();
        infos.setCustomers(mapJsonValue(entity.getCustomers(),request));
        infos.setProjects(mapJsonValue(entity.getProjects(),request));
        infos.setExperience(mapJsonValue(entity.getExperience(),request));
        infos.setSuccess(mapJsonValue(entity.getSuccess(),request));
        return infos;
    }

    public String mapImage(String imagePath){
        if(imagePath==null || imagePath.isEmpty()){
            return imagePath;
        }
        return domainName+imagePath;
    }

    public abstract void update(BannerAdminRequest request, @MappingTarget BannerEntity banner);
}
