package premium_pipe.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.UserContactEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.UserContactMapper;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.UserContactResponse;
import premium_pipe.repository.UserContactRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserContactAdminService {
    private final UserContactRepository userContactRepository;
    private final UserContactMapper userContactMapper;

    public Page<UserContactResponse> getContactList(RequestParams params) {
        Pageable pageable = PageRequest.of(params.page(),params.size());
        Page<UserContactEntity> contacts = userContactRepository.getContacts(pageable,params.search());
        return contacts.map(userContactMapper::toDTO);
    }

    public void deleteBy(Long id) {
        UserContactEntity entity = userContactRepository
                .getContact(id)
                .orElseThrow(()-> new NotFoundException("Gallery not found"));
        entity.setDeletedDate(LocalDateTime.now());
        userContactRepository.save(entity);
    }
}
