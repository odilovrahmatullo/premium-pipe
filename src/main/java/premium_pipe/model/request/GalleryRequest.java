package premium_pipe.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class GalleryRequest{
    @NotBlank private String fileId;
    private MultipartFile file;
}
