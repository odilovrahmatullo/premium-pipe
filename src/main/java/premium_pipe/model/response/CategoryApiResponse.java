package premium_pipe.model.response;

import lombok.Builder;

@Builder
public record CategoryApiResponse(Long id, String name,String image,String slug) {
}
