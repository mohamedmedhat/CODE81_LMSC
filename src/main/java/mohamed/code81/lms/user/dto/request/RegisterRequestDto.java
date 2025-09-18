package mohamed.code81.lms.user.dto.request;

import jakarta.validation.constraints.*;
import mohamed.code81.lms.user.enums.UserRole;

public record RegisterRequestDto(
        @NotBlank
        @Size(min = 4, max = 40)
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 6, max = 30)
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{6,30}$",
                message = "Password must contain letters, digits, and special characters"
        )
        String password,

        UserRole role
) {
}
