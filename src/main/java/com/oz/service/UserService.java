package com.oz.service;

import com.oz.dto.SignupDTO;
import com.oz.dto.UserDTO;
import com.oz.model.User;

import java.util.Optional;

public interface UserService {
    User createUser(SignupDTO user);

    Optional<User> getUserById(String id);

    User updateUserById(String id, User user);

    void deleteUserById(String id);

    User getUserByEmail(String email);

    boolean existsByEmail(String username);

    boolean existsByEmailAndPassword(String email, String password);
}
