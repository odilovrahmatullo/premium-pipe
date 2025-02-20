package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.PartnerEntity;
import premium_pipe.mapper.PartnerMapper;
import premium_pipe.model.response.PartnerResponse;
import premium_pipe.repository.PartnerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerService {
    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;

    public List<PartnerResponse> getPartners() {
        List<PartnerEntity> partners = (List<PartnerEntity>) partnerRepository.findAll();
        List<PartnerResponse> list = new ArrayList<>();
        for (PartnerEntity one : partners) {
            PartnerResponse partnerResponse = partnerMapper.toPartnerResponse(one);
            list.add(partnerResponse);
        }
        return list;

    }
}
