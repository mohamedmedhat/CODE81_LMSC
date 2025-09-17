package mohamed.code81.lms.log.dto.request;

import mohamed.code81.lms.auth.user.User;

public record UserActivityLogRequestDto(
        User user,
        String action
) {
}
