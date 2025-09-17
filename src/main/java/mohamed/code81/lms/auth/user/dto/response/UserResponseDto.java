package mohamed.code81.lms.auth.user.dto.response;

import mohamed.code81.lms.auth.user.enums.UserRole;

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
