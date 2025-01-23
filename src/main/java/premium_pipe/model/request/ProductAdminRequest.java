package premium_pipe.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import premium_pipe.util.validator.JsonFieldConstraint;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAdminRequest {
    @NotNull
    @JsonFieldConstraint
    private Map<String, String> name;
    @NotNull
    @JsonFieldConstraint
    private Map<String, String> description;

    @NotNull
    private Long categoryId;

    @NotNull(message = "Product info list cannot be null")
    @Size(min = 1, message = "At least one product info is required")
    private List<ProductInfoRequest> productInfos;

    @NotNull(message = "Product photos list cannot be null")
    @Size(min = 1, message = "At least one product photo is required")
    private List<String> fileIds;
}
