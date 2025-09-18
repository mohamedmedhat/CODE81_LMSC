package mohamed.code81.lms.user.dto.response;

public record LoginResponseDto(
        UserResponseDto user,
        String accessToken,
        String refreshToken
) {
}
