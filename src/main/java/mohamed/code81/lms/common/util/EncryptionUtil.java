package mohamed.code81.lms.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class EncryptionUtil {
    private final PasswordEncoder passwordEncoder;


    public String encode(String rawString){
        return passwordEncoder.encode(rawString);
    }

    public Boolean comparePasswords(String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String generateRandomToken(){
        return UUID.randomUUID().toString();
    }
}
