package premium_pipe.model.response;

import lombok.Data;

@Data
public class AboutResponse {
    private Long id;
    private String title;
    private String description1;
    private String description2;
    private String openedDate;
    private String numberOfEmployees;
    private String neighbourCountries;
    private String image;
}
