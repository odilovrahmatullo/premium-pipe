package premium_pipe.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoRequest {
    @NotNull(message = "Code value is required")
    private Integer codeValue;

    @NotNull(message = "Diameter value is required")
    private String diameterValue;

    @NotNull(message = "Package value is required")
    private String packageValue;

    @NotNull(message = "Unit value is required")
    private String unitValue;
}