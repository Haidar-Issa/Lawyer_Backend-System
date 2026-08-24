package com.web.lawyer_backend_system.controller.user;

import com.web.lawyer_backend_system.dto.ApiResponse;
import com.web.lawyer_backend_system.dto.user.UserRequestDto;
import com.web.lawyer_backend_system.dto.user.UserResponseDto;
import com.web.lawyer_backend_system.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final String path = "/api/users";

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        var user = userService.create(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.create(
                HttpStatus.CREATED,
                "User created successfully",
                user,
                path));
    }

    @PatchMapping("/update/{userId}")
    public ResponseEntity<ApiResponse<?>> updateUser(@PathVariable String userId, @RequestBody @Valid UserRequestDto userRequestDto) {
        var user = userService.update(userId,userRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "User updated successfully",
                user,
                path+ "/" + userId
        ));

    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable String userId) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "User deleted successfully",
                null,
                path));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<?>> getAll(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size) {
        Pageable pageable = PageRequest.of(page, size, org.springframework.data.domain.Sort.by("createdAt").descending());
        var users = userService.findAllByIsDeletedFalse(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "Users retrieved successfully",
                users,
                path + "/get-all"));
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserById(@PathVariable String userId) {
        var user = userService.findById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "User retrieved successfully",
                user,
                path));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lawyers")
    public ResponseEntity<ApiResponse<?>> getAllUsers(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size) {

        Pageable pageable = PageRequest.of(page, size, org.springframework.data.domain.Sort.by("createdAt").descending());
        var users = userService.findAllByIsDeletedFalse(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "Users retrieved successfully",
                users,
                path));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<?>> searchUsers(
            @RequestParam(required = false) String name) {
        var users = userService.search(name);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "Users searched successfully",
                users,
                path));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponse<?>> getUsersByRole(
            @PathVariable String role,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size) {
        Pageable pageable = PageRequest.of(page, size, org.springframework.data.domain.Sort.by("createdAt").descending());
        var users = userService.findAllByRole(role, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "Users retrieved successfully",
                users,
                path));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<?>> getUserByEmail(@PathVariable String email) {
        var user = userService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "User retrieved successfully",
                user,
                path));
    }

    @GetMapping("/lawyer/is-active")
    public ResponseEntity<ApiResponse<?>> getActiveLawyers(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, org.springframework.data.domain.Sort.by("createdAt").descending());
        var lawyers = userService.findActiveLawyers(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "Active lawyers retrieved successfully",
                lawyers,
                path));
    }

    @GetMapping("/lawyer/is-inactive")
    public ResponseEntity<ApiResponse<?>> getInactiveLawyers(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, org.springframework.data.domain.Sort.by("createdAt").descending());
        var lawyers = userService.findInactiveLawyers(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "Inactive lawyers retrieved successfully",
                lawyers,
                path));
    }

    @GetMapping("/lawyer/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> searchLawyers(
            @RequestParam String fullName
    ) {
        var lawyers = userService.findLawyersWithClientsByName(fullName);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.create(
                HttpStatus.OK,
                "Lawyers searched successfully",
                lawyers,
                path));
    }
}