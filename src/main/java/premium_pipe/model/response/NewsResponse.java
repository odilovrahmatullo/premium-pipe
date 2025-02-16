package premium_pipe.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsResponse {
    private Long id;
    private String title;
    private String image;
    private String slug;
    private String description;
    private Long viewCount;
    private LocalDateTime createdDate;
    private List<NewsResponse> otherNews;
}

