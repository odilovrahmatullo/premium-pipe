package premium_pipe.model.response.admin;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;
@Builder
public record CategoryAdminResponse(Long id,
                                    Map<String,String> name,
                                    Map<String,String> description,
                                    String image,
                                    LocalDateTime createdDate) {
}


