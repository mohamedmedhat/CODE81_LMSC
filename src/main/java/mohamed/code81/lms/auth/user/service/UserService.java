package mohamed.code81.lms.auth.user.service;

import mohamed.code81.lms.auth.user.dto.request.LoginRequestDto;
import mohamed.code81.lms.auth.user.dto.request.RegisterRequestDto;
import mohamed.code81.lms.auth.user.dto.response.LoginResponseDto;
import mohamed.code81.lms.auth.user.dto.response.UserResponseDto;
import mohamed.code81.lms.common.response.PageableResponseDto;

import java.util.UUID;

public interface UserService {
    UserResponseDto register(RegisterRequestDto dto);
    LoginResponseDto login(LoginRequestDto dto);
    UserResponseDto updateUserActivation(UUID id);
    UserResponseDto getUser(UUID id);
    PageableResponseDto<UserResponseDto> getUsers(int page, int size);
    void deleteUser(UUID id);
}
