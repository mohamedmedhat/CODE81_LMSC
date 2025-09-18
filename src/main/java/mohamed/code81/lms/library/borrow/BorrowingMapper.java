package mohamed.code81.lms.library.borrow;

import mohamed.code81.lms.common.response.PageableResponseDto;
import mohamed.code81.lms.library.book.Book;
import mohamed.code81.lms.library.borrow.dto.request.BorrowRequestDto;
import mohamed.code81.lms.library.borrow.dto.response.BorrowResponseDto;
import mohamed.code81.lms.user.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component

public class BorrowingMapper {
    public Borrowing toBorrowing(BorrowRequestDto dto, Book book, User user) {
        return Borrowing.builder()
                .book(book)
                .member(user)
                .borrowDate(LocalDate.now())
                .returnDate(dto.returnDate())
                .dueDate(dto.dueDate())
                .returned(Boolean.FALSE)
                .build();
    }

    public Borrowing updateToBorrowing(Borrowing borrowing, BorrowRequestDto dto, Book book, User user) {
        borrowing.setBook(book);
        borrowing.setMember(user);
        borrowing.setReturnDate(dto.returnDate());
        borrowing.setDueDate(dto.dueDate());
        return borrowing;
    }

    public BorrowResponseDto borrowResponseDto(Borrowing b) {
        return new BorrowResponseDto(
                b.getId(),
                b.getBook().getId(),
                b.getMember().getId(),
                b.getBorrowDate(),
                b.getReturnDate(),
                b.getReturned()
        );
    }

    public PageableResponseDto<BorrowResponseDto> toBorrowingPageableResponse(Page<Borrowing> borrowings) {
        return new PageableResponseDto<>(
                borrowings.stream()
                        .map(this::borrowResponseDto)
                        .toList(),
                borrowings.getNumber(),
                borrowings.getSize(),
                borrowings.getTotalElements(),
                borrowings.getTotalPages()
        );
    }
}
