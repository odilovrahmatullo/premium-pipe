package premium_pipe.model.response.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TranslateAdminRequest {
    @NotBlank(message = "keyword must not be null")
    private String keyword;
    private String keyUz;
    private String keyRu;
    private String keyEn;
    @NotNull(message = "choose type")
    private Long staticId;
}
