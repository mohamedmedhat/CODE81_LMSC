package mohamed.code81.lms.library.borrow.dto.request;

import java.time.LocalDate;
import java.util.UUID;

public record BorrowRequestDto(
        UUID bookId,
        UUID userId,
        LocalDate returnDate,
        LocalDate dueDate
        ) {
}
