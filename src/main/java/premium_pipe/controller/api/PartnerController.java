package premium_pipe.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import premium_pipe.model.response.PartnerResponse;
import premium_pipe.service.PartnerService;
import java.util.List;

@RestController
@RequestMapping("/api/partner")
@RequiredArgsConstructor
public class PartnerController {
    private final PartnerService partnerService;

    @GetMapping
    public ResponseEntity<List<PartnerResponse>> partners(){
       return ResponseEntity.ok(partnerService.getPartners());
    }

}
