package premium_pipe.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.GalleryEntity;
import premium_pipe.entity.UserContactEntity;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.request.UserContactRequest;
import premium_pipe.model.response.GalleryResponse;
import premium_pipe.repository.GalleryRepository;

@Service
@RequiredArgsConstructor
public class GalleryService {
    private final GalleryRepository galleryRepository;
    private final FileService fileService;

    public Page<GalleryResponse> getGalleries(@Valid RequestParams params){
        Pageable pageable = PageRequest.of(params.page(),params.size());
        Page<GalleryEntity> galleryEntities = galleryRepository.getGalleries(pageable);
        return galleryEntities.map(
                g -> GalleryResponse.builder()
                        .file(fileService.getById(g.getFile().getId()))
                        .fileType(g.getFileType())
                        .build()
        );
    }
}
