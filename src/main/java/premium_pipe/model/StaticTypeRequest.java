package premium_pipe.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StaticTypeRequest {
    @NotBlank(message = "name must not be empty")
    private String name;
}
