package premium_pipe.service.admin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.BannerEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.BannerMapper;
import premium_pipe.model.request.BannerAdminRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.admin.BannerAdminResponse;
import premium_pipe.repository.BannerRepository;
import premium_pipe.service.FileGetService;
import premium_pipe.service.FileSessionService;

@Service
@RequiredArgsConstructor
public class BannerAdminService {
    private final BannerRepository bannerRepository;
    private final FileSessionService fileSessionService;
    private final BannerMapper bannerMapper;
    private final FileGetService fileGetService;

    public void create(final BannerAdminRequest request,final HttpSession session) {
        String dropzoneKey = BannerEntity.class.getName();
        String imagePath = fileSessionService.getImage(dropzoneKey, session);
        BannerEntity banner = bannerMapper.toEntity(request);
        banner.setImage(fileGetService.normalization(imagePath));
        bannerRepository.save(banner);
        fileSessionService.deleteFilesFromSession(dropzoneKey, session);
    }

    public Page<BannerAdminResponse> getBanners(final RequestParams params){
        Pageable pageable = PageRequest.of(params.page(),params.size());
        Page<BannerEntity> banners = bannerRepository.findAll(pageable);
        return banners.map(bannerMapper::entityToResponse);
    }

    public void delete(Long id) {
        BannerEntity banner  = bannerRepository.findById(id).orElseThrow(()-> new NotFoundException("Banner not found"));
        bannerRepository.delete(banner);
    }


}
