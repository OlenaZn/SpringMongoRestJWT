package com.oz.service.impl;

import com.oz.dto.SignupDTO;
import com.oz.dto.UserDTO;
import com.oz.model.User;
import com.oz.repository.UserRepository;
import com.oz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public User createUser(final SignupDTO user){

        User newUser = new User(user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                encoder.encode(user.getPassword()),
                LocalDateTime.now());
        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> getUserById(final String id){
        return userRepository.findById(id);
    }

    @Override
    public User updateUserById(final String id, final User user){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            User existedUser = userOptional.get();
            existedUser.setFirstName(user.getFirstName());
            existedUser.setLastName(user.getLastName());
            existedUser.setEmail(user.getEmail());
            existedUser.setPassword(encoder.encode(user.getPassword()));
            return existedUser;
        }
        return null;
    }

    @Override
    public void deleteUserById(final String id){
        userRepository.deleteById(id);
    }

    public User getUserByEmail(final String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email" + email));
    }

    @Override
    public boolean existsByEmail(final String username) {
        return userRepository.existsByEmail(username);
    }

    @Override
    public boolean existsByEmailAndPassword(final String email, final String password){
        final User user = getUserByEmail(email);
        return userRepository.existsByEmail(email) && encoder.matches(password, user.getPassword());
    }

}
