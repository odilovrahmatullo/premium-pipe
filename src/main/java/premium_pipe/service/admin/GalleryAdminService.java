package premium_pipe.service.admin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.GalleryEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.GalleryMapper;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.GalleryResponse;
import premium_pipe.repository.GalleryRepository;
import premium_pipe.service.FileGetService;
import premium_pipe.service.FileSessionService;
import premium_pipe.service.FileUploadService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GalleryAdminService {
    private final GalleryRepository galleryRepository;
    private final GalleryMapper galleryMapper;
    private final FileSessionService fileSessionService;
    private final FileUploadService fileUploadService;
    private final FileGetService fileGetService;

    public void create(final HttpSession session) {
        String dropzoneKey = GalleryEntity.class.getName();
        String imagePath = fileSessionService.getImage(dropzoneKey,session);
        GalleryEntity gallery = GalleryEntity.builder()
                .image(fileGetService.normalization(imagePath))
                .fileType(fileUploadService.setFileType(imagePath))
                .build();
        galleryRepository.save(gallery);
        fileSessionService.deleteFilesFromSession(dropzoneKey, session);
    }

    public Page<GalleryResponse> getGalleries(RequestParams params) {
        Pageable pageable = PageRequest.of(params.page(), params.size());
        Page<GalleryEntity> galleries = galleryRepository.getGalleries(pageable,params.search());
        return galleries.map(galleryMapper::toDTO);
    }

    public GalleryEntity getGalleryEntity(Long id) {
        return galleryRepository.getGallery(id)
                .orElseThrow(() -> new NotFoundException("Gallery not found"));
    }

    public void delete(Long id) {
        GalleryEntity gallery = getGalleryEntity(id);
        gallery.setDeletedDate(LocalDateTime.now());
        galleryRepository.save(gallery);
    }
}
