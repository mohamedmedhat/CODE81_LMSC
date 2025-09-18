/*
 * Copyright (c) 2025 Mohamed Medhat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy at: http://www.apache.org/licenses/LICENSE-2.0
 */

package mohamed.code81.lms.user.service.impl;

import lombok.RequiredArgsConstructor;
import mohamed.code81.lms.user.User;
import mohamed.code81.lms.user.UserMapper;
import mohamed.code81.lms.user.UserRepository;
import mohamed.code81.lms.user.dto.request.LoginRequestDto;
import mohamed.code81.lms.user.dto.request.RegisterRequestDto;
import mohamed.code81.lms.user.dto.response.LoginResponseDto;
import mohamed.code81.lms.user.dto.response.UserResponseDto;
import mohamed.code81.lms.user.enums.UserRole;
import mohamed.code81.lms.user.exception.BadCredentialException;
import mohamed.code81.lms.user.exception.UserNotFoundException;
import mohamed.code81.lms.user.service.UserExplorerService;
import mohamed.code81.lms.user.service.UserRoleService;
import mohamed.code81.lms.user.service.UserService;
import mohamed.code81.lms.common.response.PageableResponseDto;
import mohamed.code81.lms.common.util.CookieUtil;
import mohamed.code81.lms.common.util.EncryptionUtil;
import mohamed.code81.lms.common.util.JwtUtil;
import mohamed.code81.lms.log.dto.request.UserActivityLogRequestDto;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public UserResponseDto register(RegisterRequestDto dto) {
        if (Boolean.TRUE.equals(userRepository.existsByEmail(dto.email()))) {
            throw new BadCredentialException("email not valid");
        }

        String encryptedPassword = encryptionUtil.encode(dto.password());

        User user = userMapper.toUser(dto, encryptedPassword);

        User savedUser =  userRepository.save(user);

        pushEvent(savedUser, "register");

        return userMapper.toUserResponse(savedUser);
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

            pushEvent(user, "login");

            return userMapper.toLoginResponse(user, accessToken, refreshToken);
        }

        throw new BadCredentialException("bad credential");
    }

    @Override
    public UserResponseDto updateUserActivation(UUID id) {
        User user = getById(id);

        user.setActive(!user.getActive());

        pushEvent(user, "update user activation");

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
        User user = getById(id);
        pushEvent(user, "delete user with id: "+id);
        userRepository.delete(user);
    }

    @Override
    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user with id: " + id + " not found"));
    }

    @Override
    public List<User> getByIds(List<UUID> id) {
        return userRepository.findByIdIn(id);
    }

    @Override
    public void updateUserRole(UUID id, UserRole role) {
        User user = getById(id);
        pushEvent(user, "update user role From: "+user.getRole()+"to: "+role);
        user.setRole(role);
        userRepository.save(user);
    }

    private void checkUserActivation(User user){
        if(Boolean.FALSE.equals(user.getActive())){
            throw new BadCredentialException("this account has been deactivated");
        }
    }

    private void pushEvent(User user, String action){
        applicationEventPublisher.publishEvent(new UserActivityLogRequestDto(user, action));
    }
}
