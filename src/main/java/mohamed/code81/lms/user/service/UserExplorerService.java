package mohamed.code81.lms.user.service;

import mohamed.code81.lms.user.User;

import java.util.List;
import java.util.UUID;

public interface UserExplorerService {
    User getById(UUID id);
    List<User> getByIds(List<UUID> id);
}
