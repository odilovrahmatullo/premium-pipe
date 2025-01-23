package premium_pipe.model.response.admin;

import lombok.Builder;
import lombok.Data;
import premium_pipe.model.response.FileResponse;
import premium_pipe.model.response.ProductInfoResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class ProductAdminResponse {
    private Long id;
    private Map<String, String> name;
    private Map<String, String> description;
    private CategoryAdminResponse category;
    private List<ProductInfoResponse> productInfos;
    private List<FileResponse> fileIds;
    private LocalDateTime createdDate;
}
