package premium_pipe.model.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsAdminResponse {
    private Long id;
    private Map<String, String> title;
    private Map<String,String> description;
    private String image;
    private Long viewCount;
    private LocalDateTime createdDate;
}
