package mohamed.code81.lms.log.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserActivityLogNotFoundException extends RuntimeException{
    public UserActivityLogNotFoundException(String msg){
        super(msg);
    }
}
