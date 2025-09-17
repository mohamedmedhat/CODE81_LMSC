package mohamed.code81.lms.user.dto.response;

import mohamed.code81.lms.user.enums.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String email,
        UserRole role,
        Boolean active,
        LocalDateTime createdAt
) {
}
