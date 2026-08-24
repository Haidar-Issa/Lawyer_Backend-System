package com.web.lawyer_backend_system.service.user;

import com.web.lawyer_backend_system.dto.user.UserRequestDto;
import com.web.lawyer_backend_system.dto.user.UserResponseDto;
import com.web.lawyer_backend_system.entity.User;
import com.web.lawyer_backend_system.enums.UserRole;
import com.web.lawyer_backend_system.mapper.user.UserMapper;
import com.web.lawyer_backend_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto create(UserRequestDto userRequestDto) {
        User user = userMapper.toUserEntity(userRequestDto);
        User savedUser = userRepository.saveAndFlush(user);
        return userMapper.toUserResponseDto(savedUser);
    }

    public UserResponseDto update(String userId, UserRequestDto userRequestDto) {
        var user = userMapper.toUserEntity(userRequestDto);
        User exsistedUser = userRepository.findByUserId(userId).orElseThrow(
                () -> new RuntimeException("UserId is not found"));
        userMapper.updateUserFromDto(userRequestDto, exsistedUser);
        return userMapper.toUserResponseDto(exsistedUser);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void delete(String userId) {
        userRepository.deleteByUserId(userId);

    }

    public Page<UserResponseDto> findAllByIsDeletedFalse(Pageable pageable) {
        Page<User> users = userRepository.findByIsDeletedFalse(pageable);
        return users.map(userMapper::toUserResponseDto);
    }

    public UserResponseDto findById(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return userMapper.toUserResponseDto(user);
    }

    public User findLawyer(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Lawyer not found with id: " + userId));
    }

    public Page<UserResponseDto> findAllByRole(String role, Pageable pageable) {
        Page<User> users = userRepository.findAllByRole(UserRole.valueOf(role), pageable);
        return users.map(userMapper::toUserResponseDto);
    }

    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return userMapper.toUserResponseDto(user);
    }

    public List<UserResponseDto> search(String fullName) {
        List<User> users = userRepository.findByFullName(fullName)
                .orElseThrow(() -> new RuntimeException("User not found with full name: " + fullName));
        return users.stream().map(userMapper::toUserResponseDto).toList();
    }

    public Page<UserResponseDto> findActiveLawyers(Pageable pageable) {
        Page<User> users = userRepository.findByActiveIsTrue(pageable);
        return users.map(userMapper::toUserResponseDto);
    }

    public Page<UserResponseDto> findInactiveLawyers(Pageable pageable) {
        Page<User> users = userRepository.findByActiveIsFalse(pageable);
        return users.map(userMapper::toUserResponseDto);
    }


    public List<UserResponseDto> findLawyersWithClientsByName(String fullName) {
        List<User> users = userRepository.findLawyersWithClientsByName(fullName);
        return users.stream().map(userMapper::toUserResponseDto).toList();
    }

    public Page<UserResponseDto> findLawyersWithClientsByName(String fullName, Pageable pageable) {
        Page<User> users = userRepository.findAllUsersByIsDeletedFalse(pageable);
        return users.map(userMapper::toUserResponseDto);
    }


}
