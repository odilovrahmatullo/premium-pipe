package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.GalleryEntity;
import premium_pipe.enums.FileType;
import premium_pipe.mapper.GalleryMapper;
import premium_pipe.model.response.GalleryListResponse;
import premium_pipe.model.response.GalleryResponse;
import premium_pipe.repository.GalleryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryService {
    private final GalleryRepository galleryRepository;
    private final GalleryMapper galleryMapper;

    public GalleryListResponse getList() {
        List<GalleryResponse> images = new ArrayList<>();
        List<GalleryResponse> videos = new ArrayList<>();
        List<GalleryEntity> galleryEntities = (List<GalleryEntity>) galleryRepository.findAll();
        for(GalleryEntity gallery : galleryEntities){
            if(gallery.getFileType().equals(FileType.IMAGE)){
                images.add(galleryMapper.toDTO(gallery));
            }else{
                videos.add(galleryMapper.toDTO(gallery));
            }
        }
        GalleryListResponse list = new GalleryListResponse();
        list.setImages(images);
        list.setVideos(videos);
        return list;

    }


}
