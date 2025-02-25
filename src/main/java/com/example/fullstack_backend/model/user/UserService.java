package com.example.fullstack_backend.model.user;

import com.example.fullstack_backend.model.cart.dtoResponse.CartDto;
import com.example.fullstack_backend.model.cart.CartRepository;
import com.example.fullstack_backend.model.order.OrderRepository;
import com.example.fullstack_backend.model.order.dtoResponse.OrderDto;
import com.example.fullstack_backend.model.user.dtoUserRequest.AddUserRequest;
import com.example.fullstack_backend.model.user.dtoUserRequest.UpdateUserRequest;
import com.example.fullstack_backend.model.user.dtoUserResponse.UserResponseDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User addUser(AddUserRequest addUserRequest) {
        if (userRepository.existsByEmail(addUserRequest.getEmail())) {
            throw new EntityExistsException("User already exists: " + addUserRequest.getEmail());
        }
        User user = createUser(addUserRequest);
        return userRepository.save(user);
    }

    private User createUser(AddUserRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }


    @Override
    public User updateUser(UpdateUserRequest userRequest, Long userId) {
        User user =  getUserById(userId);
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.delete(getUserById(userId));
    }
    @Override
    public List<UserResponseDto> convertUsersToUserDto (List<User> users){
        return users.stream().map(user -> convertToDto(user)).toList();
    }

    @Override
    public UserResponseDto convertToDto(User user){
        UserResponseDto userDto = modelMapper.map(user, UserResponseDto.class);
        List<OrderDto> ordersDto = orderRepository.findByUserId(user.getId()).stream()
                .map((element) -> modelMapper.map(element, OrderDto.class)).toList();
        cartRepository.findByUserId(user.getId())
                .filter(cart -> !cart.isEmpty())
                .ifPresent(cart -> userDto.setCart(modelMapper.map(cart, CartDto.class)));
        userDto.setOrders(ordersDto);
        return userDto;
    }
    @Override
    public User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(()-> new EntityNotFoundException("Log in required!"));
    }
}
