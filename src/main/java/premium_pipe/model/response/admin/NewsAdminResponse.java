package premium_pipe.model.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import premium_pipe.model.response.FileResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsAdminResponse {
    private Long id;
    private Map<String, String> title;
    private Map<String, String> description;
    private List<FileResponse> fileIds;
    private Long viewCount;
    private LocalDateTime createdDate;
}
