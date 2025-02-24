package com.example.fullstack_backend.model.user;

import com.example.fullstack_backend.model.user.dtoUserRequest.AddUserRequest;
import com.example.fullstack_backend.model.user.dtoUserRequest.UpdateUserRequest;
import com.example.fullstack_backend.model.user.dtoUserResponse.UserResponseDto;

import java.util.List;

public interface IUserService {
    User addUser(AddUserRequest user);
    User updateUser(UpdateUserRequest user, Long userId);
    User getUserById(Long userId);
    void deleteUser(Long userId);
    List<UserResponseDto> convertUsersToUserDto(List<User> users);
    UserResponseDto convertToDto(User user);

    User getAuthenticatedUser();
}
