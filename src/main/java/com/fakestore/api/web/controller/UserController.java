package com.fakestore.api.web.controller;

import com.fakestore.api.dto.*;
import com.fakestore.api.persistence.entity.User;
import com.fakestore.api.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @PageableDefault(size = 5) Pageable pageable
            ){
        Page<UserResponseDTO> userList = userService.getAllUsers(pageable);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){

        User createdUser = userService.getUserById(id);
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                createdUser.getId(),
                createdUser.getUsername(),
                createdUser.getEmail(),
                createdUser.getAvatar(),
                createdUser.getRole(),
                createdUser.getCreatedAt()
        );

        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreationDTO userCreationDTO){
        User createdUser = userService.createUser(userCreationDTO);
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                createdUser.getId(),
                createdUser.getUsername(),
                createdUser.getEmail(),
                createdUser.getAvatar(),
                createdUser.getRole(),
                createdUser.getCreatedAt()
        );
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO userUpdateDto){
        User user = userService.getAuthenticatedUser();

        if(!Objects.equals(user.getId(), id)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User updatedUser = userService.updateUser(id, userUpdateDto);
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                updatedUser.getId(),
                updatedUser.getUsername(),
                updatedUser.getEmail(),
                updatedUser.getAvatar(),
                updatedUser.getRole(),
                updatedUser.getCreatedAt()
        );
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("{id}/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordDTO changePasswordDTO){
        User user = userService.getAuthenticatedUser();
        if(!Objects.equals(user.getId(), id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        userService.changePassword(id, changePasswordDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/is-available")
    public ResponseEntity<Boolean> isUsernameAvailable(@RequestBody emailDTO emailDTO){
        Boolean isAvailable = userService.isUsernameAvailable(emailDTO.email());
        return new ResponseEntity<>(isAvailable, HttpStatus.OK);
    }
}
