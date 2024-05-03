package com.systemlogin.service;

import com.systemlogin.repository.UserRepository;
import com.systemlogin.usermodel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    // Implementasi untuk getAllUser()
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();  // Mengembalikan semua pengguna dari repositori
    }

    // Implementasi untuk loadUserByUsername
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    // Implementasi untuk findUserByEmail
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Implementasi untuk findUserById
    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}