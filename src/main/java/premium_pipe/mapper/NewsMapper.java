package premium_pipe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import premium_pipe.entity.NewsEntity;
import premium_pipe.model.request.NewsRequest;
import premium_pipe.model.response.admin.NewsAdminResponse;
import premium_pipe.service.FileGetService;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public abstract class NewsMapper {
    @Autowired public FileGetService fileGetService;

    public abstract NewsEntity toEntity(NewsRequest request);
    public abstract NewsAdminResponse toDTO(final NewsEntity entity);

    public abstract void updateNews(NewsRequest newsRequest, @MappingTarget final NewsEntity news);

}
