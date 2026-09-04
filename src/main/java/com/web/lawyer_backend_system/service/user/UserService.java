package com.web.lawyer_backend_system.service.user;

import com.web.lawyer_backend_system.dto.user.UserFilterDto;
import com.web.lawyer_backend_system.dto.user.UserRequestDto;
import com.web.lawyer_backend_system.dto.user.UserResponseDto;
import com.web.lawyer_backend_system.entity.User;
import com.web.lawyer_backend_system.enums.UserRole;
import com.web.lawyer_backend_system.exception.ResourceNotFoundException;
import com.web.lawyer_backend_system.mapper.user.UserMapper;
import com.web.lawyer_backend_system.repository.UserRepository;
import com.web.lawyer_backend_system.repository.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
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

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String userId) {
        if (userRepository.findByUserId(userId).isPresent()) {
            throw new ResourceNotFoundException("User is not found by id: " + userId);
        }
        userRepository.deleteByUserId(userId);
        log.info("user is deleted successfully");

    }

    @Transactional(readOnly = true)
    public Page<UserResponseDto> findAllByIsDeletedFalse(Pageable pageable) {
        Page<User> users = userRepository.findByIsDeletedFalse(pageable);
        return users.map(userMapper::toUserResponseDto);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return userMapper.toUserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public Page<UserResponseDto> search(UserFilterDto userFilterDto, Pageable pageable) {
        Specification<User> spec = UserSpecification.build(userFilterDto);

        if (spec == null) {
            throw new ResourceNotFoundException("No users found matching the search criteria");
        }

        return userRepository.findAll(spec, pageable)
                .map(userMapper::toUserResponseDto);

    }

}
