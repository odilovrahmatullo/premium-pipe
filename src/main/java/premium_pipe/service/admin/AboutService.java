package premium_pipe.service.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import premium_pipe.entity.HomeEntity;
import premium_pipe.mapper.LocalizeMapper;
import premium_pipe.model.response.AboutResponse;
import premium_pipe.repository.HomeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AboutService {
    private final HomeRepository homeRepository;
    @Value("${upload.url}")
    private  String domainName;
    private final LocalizeMapper localizeMapper;

    public AboutResponse getAbout(HttpServletRequest request) {
        List<HomeEntity> abouts = (List<HomeEntity>) homeRepository.findAll();
        if(abouts.isEmpty()) return null;
        HomeEntity home = abouts.getFirst();
        AboutResponse about = new AboutResponse();
        about.setId(home.getId());
        about.setTitle(localizeMapper.translate(home.getTitle(),request));
        about.setDescription1(localizeMapper.translate(home.getDescription(),request));
        about.setDescription2(localizeMapper.translate(home.getDescription2(),request));
        about.setImage(domainName+home.getImage());
        about.setNeighbourCountries(localizeMapper.translate(home.getNeighborCountries(),request));
        about.setOpenedDate(localizeMapper.translate(home.getOpenedDate(),request));
        about.setNumberOfEmployees(localizeMapper.translate(home.getNumberOfEmployees(),request));
        return about;
    }
}
