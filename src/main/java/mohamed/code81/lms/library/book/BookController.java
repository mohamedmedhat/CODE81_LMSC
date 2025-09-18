package mohamed.code81.lms.library.book;

import lombok.RequiredArgsConstructor;
import mohamed.code81.lms.common.response.PageableResponseDto;
import mohamed.code81.lms.library.book.dto.request.BookRequestDto;
import mohamed.code81.lms.library.book.dto.response.BookResponseDto;
import mohamed.code81.lms.library.book.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<BookResponseDto> addBook(
            @RequestBody BookRequestDto dto,
            @RequestParam("userId") UUID userId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(dto, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(
            @PathVariable UUID id,
            @RequestBody BookRequestDto dto,
            @RequestParam("userId") UUID userId
    ) {
        return ResponseEntity.ok(bookService.updateBook(id, dto, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @GetMapping
    public ResponseEntity<PageableResponseDto<BookResponseDto>> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(bookService.getBooks(page, size));
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id, @RequestParam("userId") UUID userId) {
        bookService.deleteBook(id, userId);
        return ResponseEntity.noContent().build();
    }
}
