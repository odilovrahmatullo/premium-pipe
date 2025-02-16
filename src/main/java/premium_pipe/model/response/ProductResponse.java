package premium_pipe.model.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private String slug;
    private List<ProductInfoResponse> productInfos;
    private List<String> images;
    private List<ProductResponse> otherProducts;
    private LocalDateTime createdDate;
}
