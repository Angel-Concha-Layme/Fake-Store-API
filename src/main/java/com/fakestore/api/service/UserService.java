package com.fakestore.api.service;

import com.fakestore.api.dto.UserCreationDTO;
import com.fakestore.api.dto.UserUpdateDTO;
import com.fakestore.api.exception.UserAlreadyExistsException;
import com.fakestore.api.exception.UserNotFoundException;
import com.fakestore.api.persistence.entity.User;
import com.fakestore.api.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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

        userRepository.save(user);
        return user;
    }

    public void validateUserDoesNotExistForName(String username){
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("User already exists with username: " + username);
                });
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
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
}
