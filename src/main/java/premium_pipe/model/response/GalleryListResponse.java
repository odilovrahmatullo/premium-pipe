package premium_pipe.model.response;

import lombok.Data;

import java.util.List;
@Data
public class GalleryListResponse {
    List<GalleryResponse> images;
    List<GalleryResponse> videos;
}
