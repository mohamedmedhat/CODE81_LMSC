package mohamed.code81.lms.library.borrow;

import mohamed.code81.lms.common.response.PageableResponseDto;
import mohamed.code81.lms.library.borrow.dto.request.BorrowRequestDto;
import mohamed.code81.lms.library.borrow.dto.response.BorrowResponseDto;

import java.util.UUID;

public interface BorrowService {
    BorrowResponseDto borrowBook(BorrowRequestDto dto, UUID userId);
    BorrowResponseDto updateBorrowedBook(UUID id, BorrowRequestDto dto, UUID userId);
    BorrowResponseDto getBorrowedBook(UUID id);
    PageableResponseDto<BorrowResponseDto> getBorrowedBooks(int page, int size);
    void deleteBorrowedBook(UUID id, UUID userId);
}
