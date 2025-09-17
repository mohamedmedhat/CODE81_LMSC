package mohamed.code81.lms.auth.user.service.impl;

import lombok.RequiredArgsConstructor;
import mohamed.code81.lms.auth.user.User;
import mohamed.code81.lms.auth.user.UserMapper;
import mohamed.code81.lms.auth.user.UserRepository;
import mohamed.code81.lms.auth.user.dto.request.LoginRequestDto;
import mohamed.code81.lms.auth.user.dto.request.RegisterRequestDto;
import mohamed.code81.lms.auth.user.dto.response.LoginResponseDto;
import mohamed.code81.lms.auth.user.dto.response.UserResponseDto;
import mohamed.code81.lms.auth.user.enums.UserRole;
import mohamed.code81.lms.auth.user.exception.BadCredentialException;
import mohamed.code81.lms.auth.user.exception.UserNotFoundException;
import mohamed.code81.lms.auth.user.service.UserExplorerService;
import mohamed.code81.lms.auth.user.service.UserRoleService;
import mohamed.code81.lms.auth.user.service.UserService;
import mohamed.code81.lms.common.response.PageableResponseDto;
import mohamed.code81.lms.common.util.CookieUtil;
import mohamed.code81.lms.common.util.EncryptionUtil;
import mohamed.code81.lms.common.util.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserExplorerService, UserRoleService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final EncryptionUtil encryptionUtil;

    @Override
    public UserResponseDto register(RegisterRequestDto dto) {
        if (Boolean.TRUE.equals(userRepository.existsByEmail(dto.email()))) {
            throw new BadCredentialException("email not valid");
        }

        String encryptedPassword = encryptionUtil.encode(dto.password());

        User user = userMapper.toUser(dto, encryptedPassword);

        return userMapper.toUserResponse(
                userRepository.save(user)
        );
    }

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new UserNotFoundException("user with email: " + dto.email() + " not found"));

        checkUserActivation(user);

        if (Boolean.TRUE.equals(encryptionUtil.comparePasswords(dto.password(), user.getPassword()))) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));

            String accessToken = jwtUtil.generateToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);

            cookieUtil.setInCookie(refreshToken);

            return userMapper.toLoginResponse(user, accessToken, refreshToken);
        }

        throw new BadCredentialException("bad credential");
    }

    @Override
    public UserResponseDto updateUserActivation(UUID id) {
        User user = getById(id);

        user.setActive(!user.getActive());

        return userMapper.toUserResponse(
                userRepository.save(user)
        );
    }

    @Override
    public UserResponseDto getUser(UUID id) {
        return userMapper.toUserResponse(getById(id));
    }

    @Override
    public PageableResponseDto<UserResponseDto> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
        return userMapper.toUserPageableResponse(users);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.delete(getById(id));
    }

    @Override
    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user with id: " + id + " not found"));
    }

    @Override
    public void updateUserRole(UUID id, UserRole role) {
        User user = getById(id);
        user.setRole(role);
        userRepository.save(user);
    }

    private void checkUserActivation(User user){
        if(Boolean.FALSE.equals(user.getActive())){
            throw new BadCredentialException("this account has been deactivated");
        }
    }
}
