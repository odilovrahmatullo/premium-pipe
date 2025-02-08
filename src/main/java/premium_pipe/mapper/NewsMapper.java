package premium_pipe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import premium_pipe.entity.NewsEntity;
import premium_pipe.model.request.NewsRequest;
import premium_pipe.model.response.admin.NewsAdminResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public abstract class NewsMapper {

    public abstract NewsEntity toEntity(NewsRequest request);
    public abstract NewsAdminResponse toDTO(final NewsEntity entity);
    public abstract void updateNews(NewsRequest newsRequest, @MappingTarget final NewsEntity news);

}
