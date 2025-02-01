package premium_pipe.model.response.admin;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeAdminResponse {
    private Long id;
    private Map<String, String> title;
    private Map<String, String> description;
    private Map<String, String> experience;
    private Map<String, String> numberOfProjects;
    private Map<String, String> numberOfCustomers;
    private Map<String, String> openedDate;
    private Map<String, String> numberOfEmployees;
    private Map<String, String> neighborCountries;
    private String image;
    private LocalDateTime createdDate;
}
