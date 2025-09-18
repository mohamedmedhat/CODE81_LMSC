package mohamed.code81.lms.library.borrow.dto.response;

import mohamed.code81.lms.library.book.Book;
import mohamed.code81.lms.user.User;

public record GetMemberAndBookResponseDto(
        User member,
        Book book
) {
}
