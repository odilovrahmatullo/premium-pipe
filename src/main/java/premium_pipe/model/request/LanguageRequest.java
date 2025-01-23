package premium_pipe.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LanguageRequest {
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String code;
    private Boolean isActive;
    private Boolean isDefault;
}