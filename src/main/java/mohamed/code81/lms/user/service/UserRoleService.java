package mohamed.code81.lms.user.service;

import mohamed.code81.lms.user.enums.UserRole;

import java.util.UUID;

public interface UserRoleService {
    void updateUserRole(UUID id, UserRole role);
}
