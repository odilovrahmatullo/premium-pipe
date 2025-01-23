package premium_pipe.model.response;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record NewsResponse(Long id, String title, String description, Long viewCount, LocalDateTime createdDate) {
}
