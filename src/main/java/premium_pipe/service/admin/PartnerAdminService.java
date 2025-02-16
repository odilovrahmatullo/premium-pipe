package premium_pipe.service.admin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.PartnerEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.PartnerMapper;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.PartnerResponse;
import premium_pipe.repository.PartnerRepository;
import premium_pipe.service.FileGetService;
import premium_pipe.service.FileSessionService;

@Service
@RequiredArgsConstructor
public class PartnerAdminService {
    private final PartnerRepository partnerRepository;
    private final FileSessionService fileSessionService;
    private final PartnerMapper partnerMapper;
    private final FileGetService fileGetService;

    public void create(HttpSession session) {
        String dropzoneKey = PartnerEntity.class.getName();
        String imagePath = fileSessionService.getImage(dropzoneKey,session);
        PartnerEntity partner = PartnerEntity.builder()
                .image(fileGetService.normalization(imagePath))
                .build();
        partnerRepository.save(partner);
    }

    public Page<PartnerResponse> getAll(RequestParams params) {
        Pageable pageable = PageRequest.of(params.page(),params.size());
        Page<PartnerEntity> partners = partnerRepository.getList(pageable);
        return partners.map(partnerMapper::toDTO);
    }

    public void delete(Long id) {
        PartnerEntity partner = partnerRepository.findById(id).orElseThrow(()-> new NotFoundException("Partner not found"));
        partnerRepository.delete(partner);
    }
}
