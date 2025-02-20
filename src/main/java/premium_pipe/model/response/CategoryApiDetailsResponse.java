package premium_pipe.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryApiDetailsResponse {
    private Long id;
    private String name;
    private String description;
    private String image;
    private String slug;
    private List<CategoryApiDetailsResponse> allCategories;
    private List<ProductResponse> products;
}
