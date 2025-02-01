package premium_pipe.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import premium_pipe.util.validator.JsonFieldConstraint;
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
}
