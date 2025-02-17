package premium_pipe.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.BannerEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.BannerMapper;
import premium_pipe.model.response.BannerResponse;
import premium_pipe.repository.BannerRepository;


@Service
@RequiredArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;
    public BannerResponse getOne(HttpServletRequest request) {
        BannerEntity banner = bannerRepository.getBanner().orElseThrow(() -> new NotFoundException("Banner not found"));
        return bannerMapper.entityToResponse(banner,request);
    }
}
