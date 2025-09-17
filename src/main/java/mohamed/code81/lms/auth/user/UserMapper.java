package mohamed.code81.lms.auth.user;

import mohamed.code81.lms.auth.user.dto.request.RegisterRequestDto;
import mohamed.code81.lms.auth.user.dto.response.LoginResponseDto;
import mohamed.code81.lms.auth.user.dto.response.UserResponseDto;
import mohamed.code81.lms.auth.user.enums.UserRole;
import mohamed.code81.lms.common.response.PageableResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(RegisterRequestDto requestDto, String encryptedPassword) {
        User user = new User();
        user.setName(requestDto.name());
        user.setEmail(requestDto.email());
        user.setPassword(encryptedPassword);
        user.setRole(requestDto.role() != null ? requestDto.role() : UserRole.MEMBER);
        return user;
    }

    public UserResponseDto toUserResponse(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getActive(),
                user.getCreatedAt()
        );
    }

    public LoginResponseDto toLoginResponse(User user, String accessToken, String refreshToken) {
        return new LoginResponseDto(
                this.toUserResponse(user),
                accessToken,
                refreshToken
        );
    }

    public PageableResponseDto<UserResponseDto> toUserPageableResponse(Page<User> users) {
        return new PageableResponseDto<>(
                users.stream()
                        .map(this::toUserResponse)
                        .toList(),
                users.getNumber(),
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages()
        );
    }

}
