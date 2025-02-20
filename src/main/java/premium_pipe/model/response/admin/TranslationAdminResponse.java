package premium_pipe.model.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import premium_pipe.entity.StaticTypeEntity;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TranslationAdminResponse {
    private Long id;
    private String keyword;
    private String keyUz;
    private String keyRu;
    private String keyEn;
    private StaticTypeEntity staticTypeName;

    public Long getStaticId() {
        return (staticTypeName != null) ? staticTypeName.getId() : null;
    }
}

