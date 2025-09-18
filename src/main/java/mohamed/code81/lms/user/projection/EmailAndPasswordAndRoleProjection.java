package mohamed.code81.lms.user.projection;

import mohamed.code81.lms.user.enums.UserRole;

public record EmailAndPasswordAndRoleProjection(
        String email,
        String password,
        UserRole role
) {
}
