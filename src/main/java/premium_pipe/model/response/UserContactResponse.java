package premium_pipe.model.response;

import java.time.LocalDateTime;

public record UserContactResponse(Long id, String name, String phoneNumber, String email, String message, LocalDateTime createdDate) {
}
