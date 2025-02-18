package premium_pipe.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInfoResponse {
    private Long id;
    private Long productId;
    private Integer codeValue;
    private String diameterValue;
    private String packageValue;
    private String unitValue;
    private Integer quantity;
    private Boolean isOrdered;
}