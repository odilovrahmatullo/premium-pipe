package premium_pipe.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import premium_pipe.util.validator.JsonFieldConstraint;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoRequest {

    @NotNull
    @JsonFieldConstraint
    private Integer codeValue;

    @NotNull
    @JsonFieldConstraint
    private String diameterValue;

    @NotNull
    @JsonFieldConstraint
    private String packageValue;

    @NotNull
    @JsonFieldConstraint
    private String unitValue;
}