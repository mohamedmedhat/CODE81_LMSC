package mohamed.code81.lms.common.exception;

import mohamed.code81.lms.category.exception.CategoryNotFoundException;
import mohamed.code81.lms.common.response.ErrorResponseDto;
import mohamed.code81.lms.library.book.exception.BookNotFoundException;
import mohamed.code81.lms.library.borrow.exception.BorrowingNotFoundException;
import mohamed.code81.lms.log.exception.UserActivityLogNotFoundException;
import mohamed.code81.lms.user.exception.BadCredentialException;
import mohamed.code81.lms.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFound(UserNotFoundException e) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserActivityLogNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserActivityLogNotFound(UserActivityLogNotFoundException e) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCategoryNotFound(CategoryNotFoundException e) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleBookNotFound(BookNotFoundException e) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BorrowingNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleBorrowingNotFound(BorrowingNotFoundException e) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredential(BadCredentialException e) {
        return buildErrorResponse(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(Exception ex, HttpStatus status) {
        ErrorResponseDto error = new ErrorResponseDto(
                status.value(),
                ex.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(error, status);
    }

}
