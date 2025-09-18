package mohamed.code81.lms.category.service;

import mohamed.code81.lms.category.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryExplorerService {
    Category getById(UUID id);
    List<Category> getByNames(List<String> names);
}
