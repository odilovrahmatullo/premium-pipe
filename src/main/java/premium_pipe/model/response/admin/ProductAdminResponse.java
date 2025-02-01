package premium_pipe.model.response.admin;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class ProductAdminResponse {
    private Long id;
    private Map<String, String> name;
    private Map<String, String> description;
    private List<String> images;
    private CategoryAdminResponse category;
    private LocalDateTime createdDate;

    public Long getCategoryId(){
        return category != null ? category.id() : null;
    }
}
