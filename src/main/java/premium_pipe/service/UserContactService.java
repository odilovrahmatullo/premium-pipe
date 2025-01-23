package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.UserContactEntity;
import premium_pipe.model.request.UserContactRequest;
import premium_pipe.repository.UserContactRepository;

@Service
@RequiredArgsConstructor
public class UserContactService {
    private final UserContactRepository userContactRepository;

    public void save(UserContactRequest contact) {
        UserContactEntity user = UserContactEntity.builder()
                .name(contact.getName())
                .email(contact.getEmail())
                .phoneNumber(contact.getPhoneNumber())
                .message(contact.getMessage())
                .build();
        userContactRepository.save(user);
    }
}
