package premium_pipe.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import premium_pipe.model.request.UserContactRequest;
import premium_pipe.service.UserContactService;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class UserContactController {
    private final UserContactService userContactService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserContactRequest contact){
        try{
            userContactService.save(contact);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
