package premium_pipe.model.response.admin;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class BannerAdminResponse {
    private Long id;
    private String image;
    private Map<String, String> title;
    private Map<String, String> description;
    private Map<String, String> success;
    private Map<String, String> experience;
    private Map<String, String> projects;
    private Map<String, String> customers;
    private LocalDateTime createdDate;
}
