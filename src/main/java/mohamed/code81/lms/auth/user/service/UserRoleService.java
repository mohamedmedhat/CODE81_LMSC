package mohamed.code81.lms.auth.user.service;

import mohamed.code81.lms.auth.user.enums.UserRole;

import java.util.UUID;

public interface UserRoleService {
    void updateUserRole(UUID id, UserRole role);
}
