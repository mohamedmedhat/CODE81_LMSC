package mohamed.code81.lms.auth.user.projection;

import mohamed.code81.lms.auth.user.enums.UserRole;

public record EmailAndPasswordAndRoleProjection(
        String email,
        String password,
        UserRole role
) {
}
