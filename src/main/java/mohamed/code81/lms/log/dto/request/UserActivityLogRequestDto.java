package mohamed.code81.lms.log.dto.request;

import mohamed.code81.lms.user.User;

public record UserActivityLogRequestDto(
        User user,
        String action
) {
}
