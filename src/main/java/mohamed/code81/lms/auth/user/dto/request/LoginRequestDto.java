package mohamed.code81.lms.auth.user.dto.request;

import jakarta.validation.constraints.*;

public record LoginRequestDto(
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 6, max = 30)
        String password
) {
}
