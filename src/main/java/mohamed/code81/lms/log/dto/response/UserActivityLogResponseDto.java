package mohamed.code81.lms.log.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserActivityLogResponseDto(
        UUID id,
        UUID userId,
        String action,
        LocalDateTime createdAt
) {
}
