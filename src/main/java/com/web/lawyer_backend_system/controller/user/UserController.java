package com.web.lawyer_backend_system.controller.user;

import com.web.lawyer_backend_system.dto.ApiResponse;
import com.web.lawyer_backend_system.dto.user.UserFilterDto;
import com.web.lawyer_backend_system.dto.user.UserRequestDto;
import com.web.lawyer_backend_system.dto.user.UserResponseDto;
import com.web.lawyer_backend_system.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@RequestBody @Valid UserRequestDto userRequestDto,
                                                                   HttpServletRequest request) {
        var user = userService.create(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.create(
                HttpStatus.CREATED,
                "User created successfully",
                user,
                request.getRequestURI()));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> updateUser(@PathVariable String userId, @RequestBody @Valid UserRequestDto userRequestDto, HttpServletRequest request) {
        var user = userService.update(userId, userRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "User updated successfully",
                user,
                request.getRequestURI()
        ));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable String userId, HttpServletRequest request) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "User deleted successfully",
                null,
                request.getRequestURI()));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserById(@PathVariable String userId, HttpServletRequest request) {
        var user = userService.findById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "User retrieved successfully",
                user,
                request.getRequestURI()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllUsers(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size,
            HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, size, org.springframework.data.domain.Sort.by("createdAt").descending());
        var users = userService.findAllByIsDeletedFalse(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "Users retrieved successfully",
                users,
                request.getRequestURI()));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<UserResponseDto>>> searchUsers(
            @ModelAttribute UserFilterDto userFilterDto,
            @PageableDefault(size = 8, sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request) {
        var users = userService.search(userFilterDto, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "Users retrieved successfully",
                users,
                request.getRequestURI()));
    }
}