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
    @NotNull
    @JsonFieldConstraint
    private Map<String, String> description;
    @NotNull
    @JsonFieldConstraint
    private Map<String, String> experience;
    @NotNull
    @JsonFieldConstraint
    private Map<String, String> numberOfProjects;
    @NotNull
    @JsonFieldConstraint
    private Map<String, String> numberOfCustomers;
    @NotNull
    @JsonFieldConstraint
    private Map<String, String> openedDate;
    @NotNull
    @JsonFieldConstraint
    private Map<String, String> numberOfEmployees;
    @NotNull
    @JsonFieldConstraint
    private Map<String, String> neighborCountries;


}
