package premium_pipe.service.admin;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.HomeEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.HomeMapper;
import premium_pipe.model.request.HomeRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.admin.HomeAdminResponse;
import premium_pipe.repository.HomeRepository;
import premium_pipe.service.FileDeleteService;
import premium_pipe.service.FileGetService;
import premium_pipe.service.FileSessionService;

@Service
@RequiredArgsConstructor
public class HomeAdminService {
    private final HomeRepository homeRepository;
    private final FileSessionService fileSessionService;
    private final HomeMapper homeMapper;
    private final FileGetService fileGetService;
    private final FileDeleteService fileDeleteService;

    public void create(HomeRequest homeRequest, HttpSession session) {
        String dropzoneKey = HomeEntity.class.getName();
        String image =  fileSessionService.getImage(dropzoneKey,session);
        HomeEntity home = homeMapper.toEntity(homeRequest);
        home.setImage(fileGetService.normalization(image));
        homeRepository.save(home);
        fileSessionService.deleteFilesFromSession(dropzoneKey,session);
    }

    public Page<HomeAdminResponse> getHomes(@Valid RequestParams params) {
        Pageable pagination = PageRequest.of(params.page(),params.size());
        Page<HomeEntity> homes = homeRepository.getAll(pagination, params.search());
        return homes.map(homeMapper::toDTO);
    }


    public HomeEntity getHome(Long id) {
        return homeRepository.findById(id).orElseThrow(()-> new NotFoundException("Not found"));
    }

    public void delete(HomeEntity home) {
        homeRepository.delete(home);
    }

    public void update(HomeEntity home, HomeRequest request, HttpSession session) {
        String dropzoneKey = HomeEntity.class.getName();
        String image = fileSessionService.getImage(dropzoneKey,session);
        homeMapper.update(request,home);
        if(image!=null){
            home.setImage(fileGetService.normalization(image));
        }
        homeRepository.save(home);
        fileSessionService.deleteFilesFromSession(dropzoneKey,session);
    }

    public void deleteImage(Long id) {
        HomeEntity home = getHome(id);
        fileDeleteService.deleteFile(home.getImage());
        homeRepository.save(home);
    }
}

