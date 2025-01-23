package premium_pipe.model.response;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class FileResponse {
   private String id;
   private String url;
   private String path;
   private String originName;
   private Long size;
   private LocalDateTime createdDate;

}
