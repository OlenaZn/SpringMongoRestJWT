package com.oz.controller;

import com.oz.controller.converter.impl.DefaultUserDtoToUserEntityConverter;
import com.oz.controller.converter.impl.DefaultUserEntityToUserDTOConverter;
import com.oz.dto.UserDTO;
import com.oz.dto.response.MessageResponseDTO;
import com.oz.model.User;
import com.oz.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String USER_DELETED_MSG = "User deleted!";
    private static final String USER_LOGOUT_MSG = "Logout successful";

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DefaultUserDtoToUserEntityConverter dtoToEntityConverter;

    @Autowired
    private DefaultUserEntityToUserDTOConverter entityToDtoConverter;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") String id) {
        Optional<User> userOptional = userService.getUserById(id);
        return userOptional.map(user -> ResponseEntity.ok(entityToDtoConverter.convert(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable("id") String id, @RequestBody UserDTO userDTO) {
        User user = userService.updateUserById(id, dtoToEntityConverter.convert(userDTO));
        if (user != null) {
            return ResponseEntity.ok(entityToDtoConverter.convert(user));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteUserById(@PathVariable("id") String id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok(new MessageResponseDTO(USER_DELETED_MSG));
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponseDTO> logoutUser() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok(new MessageResponseDTO(USER_LOGOUT_MSG));
    }

}
