package premium_pipe.model.response.admin;

import lombok.Data;

@Data
public class TranslateAdminRequest {
    private String keyword;
    private String keyUz;
    private String keyRu;
    private String keyEn;
    private Long staticId;
}
