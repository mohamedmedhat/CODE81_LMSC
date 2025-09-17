package mohamed.code81.lms.auth.user.service;

import mohamed.code81.lms.auth.user.User;

import java.util.UUID;

public interface UserExplorerService {
    User getById(UUID id);
}
