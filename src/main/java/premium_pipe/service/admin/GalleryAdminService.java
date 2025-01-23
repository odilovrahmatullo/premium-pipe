package premium_pipe.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.FileEntity;
import premium_pipe.entity.GalleryEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.GalleryMapper;
import premium_pipe.model.request.GalleryRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.GalleryResponse;
import premium_pipe.repository.GalleryRepository;
import premium_pipe.service.FileService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GalleryAdminService {
    private final GalleryRepository galleryRepository;
    private final FileService fileService;
    private final GalleryMapper galleryMapper;

    public void create(GalleryRequest request) {
        FileEntity entity = fileService.getFileEntity(request.fileId());
        GalleryEntity gallery = GalleryEntity.builder()
                .file(entity)
                .fileType(fileService.setFileType(entity))
                .build();
        galleryRepository.save(gallery);
    }

    public Page<GalleryResponse> getGalleries(RequestParams params) {
        Pageable pageable = PageRequest.of(params.page(), params.size());
        Page<GalleryEntity> galleries = galleryRepository.getGalleries(pageable);
        return galleries.map(galleryMapper::toDTO);
    }

    public GalleryResponse getGalleryResponse(Long id) {
        GalleryEntity gallery = getGalleryEntity(id);
        return galleryMapper.toDTO(gallery);
    }

    public GalleryEntity getGalleryEntity(Long id) {
        return galleryRepository.getGallery(id)
                .orElseThrow(() -> new NotFoundException("Gallery not found"));
    }

    public void update(Long id, GalleryRequest request) {
        GalleryEntity gallery = getGalleryEntity(id);
        FileEntity entity = fileService.getFileEntity(request.fileId());
        gallery.setFile(entity);
        gallery.setFileType(fileService.setFileType(entity));
        galleryRepository.save(gallery);
    }

    public void delete(Long id) {
        GalleryEntity gallery = getGalleryEntity(id);
        gallery.setDeletedDate(LocalDateTime.now());
        galleryRepository.save(gallery);
    }
}
