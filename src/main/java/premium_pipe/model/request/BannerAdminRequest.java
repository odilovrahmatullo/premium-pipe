package premium_pipe.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import premium_pipe.util.validator.JsonFieldConstraint;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BannerAdminRequest {
    @NotNull
    @JsonFieldConstraint
    private Map<String, String> title;
    private Map<String, String> description;
    private Map<String, String> success;
    private Map<String, String> experience;
    private Map<String, String> projects;
    private Map<String, String> customers;
}
