package mohamed.code81.lms.user.service;

import mohamed.code81.lms.user.User;

import java.util.UUID;

public interface UserExplorerService {
    User getById(UUID id);
}
