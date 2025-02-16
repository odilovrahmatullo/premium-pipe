package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.repository.BannerRepository;

@Service
@RequiredArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;


}
