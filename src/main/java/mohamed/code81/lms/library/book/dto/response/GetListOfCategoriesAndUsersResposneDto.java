package mohamed.code81.lms.library.book.dto.response;

import mohamed.code81.lms.category.Category;
import mohamed.code81.lms.user.User;

import java.util.List;

public record GetListOfCategoriesAndUsersResposneDto(
        List<Category> categories,
        List<User> users
) {
}
