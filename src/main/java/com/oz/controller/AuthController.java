package com.oz.controller;

import com.oz.dto.LoginDTO;
import com.oz.dto.SignupDTO;
import com.oz.dto.response.JwtResponseDTO;
import com.oz.dto.response.MessageResponseDTO;
import com.oz.security.DefaultUserDetails;
import com.oz.security.jwt.JwtUtils;
import com.oz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final String DUPLICATE_EMAIL_ERR_MSG = "Error: Email is already in use!";
    private static final String USER_REGISTERED_MSG = "User registered successfully!";
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponseDTO> authenticateUser(@Valid @RequestBody LoginDTO loginRequest) {

        if (userService.existsByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            DefaultUserDetails userDetails = (DefaultUserDetails) authentication.getPrincipal();

            return ResponseEntity.ok(new JwtResponseDTO(jwt,
                    userDetails.getId(),
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    userDetails.getUsername()));
        }
        return ResponseEntity.badRequest().body(null);
    }


    @PostMapping("/signup")
    public ResponseEntity<MessageResponseDTO> registerUser(@Valid @RequestBody SignupDTO signUpRequest) {
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseDTO(DUPLICATE_EMAIL_ERR_MSG));
        }
        userService.createUser(signUpRequest);

        return ResponseEntity.ok(new MessageResponseDTO(USER_REGISTERED_MSG));
    }
}
