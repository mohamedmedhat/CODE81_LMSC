package mohamed.code81.lms.library.borrow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class BorrowingNotFoundException extends RuntimeException {
    public BorrowingNotFoundException(String msg){
        super(msg);
    }
}
