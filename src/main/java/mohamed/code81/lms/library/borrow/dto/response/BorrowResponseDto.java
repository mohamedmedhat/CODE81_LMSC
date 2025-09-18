package mohamed.code81.lms.library.borrow.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record BorrowResponseDto(
        UUID id,
        UUID bookId,
        UUID memberId,
        LocalDate borrowDate,
        LocalDate returnDate,
        Boolean returned
) {
}
