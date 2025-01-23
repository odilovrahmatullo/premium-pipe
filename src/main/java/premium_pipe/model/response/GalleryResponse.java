package premium_pipe.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import premium_pipe.enums.FileType;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GalleryResponse {
   private Long id;
   private FileResponse file;
   private FileType fileType;
   private LocalDateTime createdDate;
}
