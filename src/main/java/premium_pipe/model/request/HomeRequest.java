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
public class HomeRequest {
    @NotNull
    @JsonFieldConstraint
    private Map<String, String> title;

    private Map<String, String> description;

    private Map<String, String> description2;

    private Map<String, String> openedDate;

    private Map<String, String> numberOfEmployees;

    private Map<String, String> neighborCountries;


}
