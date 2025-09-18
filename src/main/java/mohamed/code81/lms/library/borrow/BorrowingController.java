package mohamed.code81.lms.library.borrow;

import lombok.RequiredArgsConstructor;
import mohamed.code81.lms.common.response.PageableResponseDto;
import mohamed.code81.lms.library.borrow.dto.request.BorrowRequestDto;
import mohamed.code81.lms.library.borrow.dto.response.BorrowResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/borrowings")
public class BorrowingController {
    private final BorrowService borrowService;

    @PostMapping
    public ResponseEntity<BorrowResponseDto> borrowBook(
            @RequestBody BorrowRequestDto dto,
            @RequestParam("userId") UUID userId
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowService.borrowBook(dto, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorrowResponseDto> updateBorrowedBook(
            @PathVariable UUID id,
            @RequestBody BorrowRequestDto dto,
            @RequestParam("userId") UUID userId
            ) {
        return ResponseEntity.ok(borrowService.updateBorrowedBook(id, dto, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowResponseDto> getBorrowedBook(@PathVariable UUID id) {
        return ResponseEntity.ok(borrowService.getBorrowedBook(id));
    }

    @GetMapping
    public ResponseEntity<PageableResponseDto<BorrowResponseDto>> getBorrowedBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(borrowService.getBorrowedBooks(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowedBook(@PathVariable UUID id, @RequestParam("userId") UUID userId) {
        borrowService.deleteBorrowedBook(id, userId);
        return ResponseEntity.noContent().build();
    }
}
