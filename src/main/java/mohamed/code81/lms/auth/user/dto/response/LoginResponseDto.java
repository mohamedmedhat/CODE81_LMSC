package mohamed.code81.lms.auth.user.dto.response;

public record LoginResponseDto(
        UserResponseDto user,
        String accessToken,
        String refreshToken
) {
}
