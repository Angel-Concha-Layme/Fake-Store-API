package com.fakestore.api.service;

import com.fakestore.api.dto.ChangePasswordDTO;
import com.fakestore.api.dto.UserCreationDTO;
import com.fakestore.api.dto.UserResponseDTO;
import com.fakestore.api.dto.UserUpdateDTO;
import com.fakestore.api.exception.UserAlreadyExistsException;
import com.fakestore.api.exception.UserNotFoundException;
import com.fakestore.api.persistence.entity.User;
import com.fakestore.api.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(UserCreationDTO userDto) {
        validateUserDoesNotExistForName(userDto.username());

        User user = new User();

        user.setUsername(userDto.username());
        // ecript password with bcrypt encoder in class SecurityConfig.java
        user.setPassword(passwordEncoder.encode(userDto.password()));

        user.setEmail(userDto.email());
        user.setCreatedAt(LocalDate.now());
        user.setAvatar(userDto.avatar());
        user.setRole(userDto.role());

        userRepository.save(user);
        return user;
    }

    public void validateUserDoesNotExistForName(String username){
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("User already exists with username: " + username);
                });
    }

    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<User> userList = userRepository.findAll(pageable);
        return userList.map(this::convertToDto);
    }

    public UserResponseDTO convertToDto(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAvatar(),
                user.getRole(),
                user.getCreatedAt()
        );
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with ID: " + id)
        );
    }

    public User updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User user = getUserById(id);
        user.setUsername(userUpdateDTO.username());
        user.setEmail(userUpdateDTO.email());
        userRepository.save(user);
        return user;
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailAuth = authentication.getName();
        return userRepository.findOneByEmail(emailAuth)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + emailAuth));
    }

    public void changePassword(Long id, ChangePasswordDTO changePasswordDTO) {
        User user = getUserById(id);
        user.setPassword(passwordEncoder.encode(changePasswordDTO.newPassword()));
        userRepository.save(user);
    }

    public Boolean isUsernameAvailable(String email) {
        return userRepository.existsByEmail(email);
    }
}
