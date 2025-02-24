package com.example.fullstack_backend.controller;

import com.example.fullstack_backend.model.product.dtoRespone.ApiResponse;
import com.example.fullstack_backend.model.user.IUserService;
import com.example.fullstack_backend.model.user.User;
import com.example.fullstack_backend.model.user.dtoUserRequest.AddUserRequest;
import com.example.fullstack_backend.model.user.dtoUserRequest.UpdateUserRequest;
import com.example.fullstack_backend.model.user.dtoUserResponse.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        UserResponseDto userDto = userService.convertToDto(user);
        return ResponseEntity.ok(new ApiResponse("User found: ", userDto));
    }
    @PostMapping("")
    public ResponseEntity<ApiResponse> addUser(@RequestBody AddUserRequest userRequest) {
        User user = userService.addUser(userRequest);
        UserResponseDto userDto = userService.convertToDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("User added: ", userDto));
    }
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest userRequest, @PathVariable Long userId) {
        User user = userService.updateUser(userRequest, userId);
        UserResponseDto userDto = userService.convertToDto(user);
        return ResponseEntity.ok(new ApiResponse("User updated: ", userDto));
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
