package premium_pipe.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import premium_pipe.util.validator.JsonFieldConstraint;

import java.util.List;
import java.util.Map;

@Data
public class NewsRequest {
    @NotNull
    @JsonFieldConstraint
    private Map<String, String> title;
    @NotNull
    @JsonFieldConstraint
    private Map<String, String> description;
    @NotNull(message = "Product photos list cannot be null")
    @Size(min = 1, message = "At least one product photo is required")
    private List<String> fileIds;
}
