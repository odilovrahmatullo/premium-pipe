package premium_pipe.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BannerResponse {
    private Long id;
    private String title;
    private String description;
    private String image;
    private BannerInfo info;
    private LocalDateTime createdDate;
}
