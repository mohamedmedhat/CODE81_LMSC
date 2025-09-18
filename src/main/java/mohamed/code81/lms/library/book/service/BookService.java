package mohamed.code81.lms.library.book.service;

import mohamed.code81.lms.common.response.PageableResponseDto;
import mohamed.code81.lms.library.book.dto.request.BookRequestDto;
import mohamed.code81.lms.library.book.dto.response.BookResponseDto;

import java.util.UUID;

public interface BookService {
    BookResponseDto addBook(BookRequestDto dto, UUID userId);
    BookResponseDto updateBook(UUID id, BookRequestDto dto, UUID userId);
    BookResponseDto getBook(UUID id);
    PageableResponseDto<BookResponseDto> getBooks(int page, int size);
    void deleteBook(UUID id, UUID userId);
}
