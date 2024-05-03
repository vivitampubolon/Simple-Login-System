package com.systemlogin.controller;

import com.systemlogin.SecurityConfig.JwtProvider;
import com.systemlogin.repository.UserRepository;
import com.systemlogin.response.AuthResponse;
import com.systemlogin.service.UserService;
import com.systemlogin.service.UserServiceImplementation;
import com.systemlogin.usermodel.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserServiceImplementation customUserDetails;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {
        // Logika pendaftaran pengguna
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(409).body(new AuthResponse(null, "Email sudah digunakan", false));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new AuthResponse("Token", "Pendaftaran berhasil", true));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody User user) {
        // Logika login pengguna
        UserDetails userDetails = customUserDetails.loadUserByUsername(user.getEmail());
        if (!passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Kata sandi salah");
        }

        // Menghasilkan token dan mengirimkannya
        return ResponseEntity.ok(new AuthResponse("Token", "Login berhasil", true));
    }

    private Authentication authenticate(String username, String password) {

        System.out.println(username+"---++----"+password);

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        System.out.println("Sig in in user details"+ userDetails);

        if(userDetails == null) {
            System.out.println("Sign in details - null" + userDetails);

            throw new BadCredentialsException("Invalid username and password");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            System.out.println("Sign in userDetails - password mismatch"+userDetails);

            throw new BadCredentialsException("Invalid password");

        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }

}
