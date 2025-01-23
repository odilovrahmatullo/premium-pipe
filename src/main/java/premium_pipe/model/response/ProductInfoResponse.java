package premium_pipe.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoResponse {
    private Long id;
    private Long productId;
    private Integer codeValue;
    private String diameterValue;
    private String packageValue;
    private String unitValue;
}