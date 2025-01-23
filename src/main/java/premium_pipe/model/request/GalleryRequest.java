package premium_pipe.model.request;

import jakarta.validation.constraints.NotBlank;

public record GalleryRequest(@NotBlank String fileId){
}
