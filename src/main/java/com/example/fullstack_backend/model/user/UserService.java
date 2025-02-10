package com.example.fullstack_backend.model.user;

import com.example.fullstack_backend.model.user.dtoUserRequest.AddUserRequest;
import com.example.fullstack_backend.model.user.dtoUserRequest.UpdateUserRequest;
import com.example.fullstack_backend.model.user.dtoUserResponse.UserResponseDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    private static User createUser(AddUserRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    @Override
    public User addUser(AddUserRequest addUserRequest) {
        return Optional.of(addUserRequest).filter(ignored -> !userRepository
                        .existsByEmail(addUserRequest.getEmail()))
                .map(userRequest -> {
                    User user = createUser(userRequest);
                    return userRepository.save(user);
                }).orElseThrow(() -> new EntityExistsException("User already exists: " + addUserRequest.getEmail()));

    }

    @Override
    public User updateUser(UpdateUserRequest userRequest, Long userId) {
        return userRepository.findById(userId).map(user -> {
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            return userRepository.save(user);
        }).orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new EntityNotFoundException("User not found");
                });
    }
    @Override
    public List<UserResponseDto> convertUsersToUserDto (List<User> users){
        return users.stream().map(user -> convertToDto(user)).toList();
    }

    @Override
    public UserResponseDto convertToDto(User user){
        UserResponseDto userDto = modelMapper.map(user, UserResponseDto.class);
        return userDto;
    }
}
