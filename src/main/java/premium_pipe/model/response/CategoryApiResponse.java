package premium_pipe.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryApiResponse(Long id, String name,String description,String image,String slug) {
}
