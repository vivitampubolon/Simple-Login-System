package com.systemlogin.service;

import com.systemlogin.usermodel.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser();  // Mengembalikan daftar semua pengguna

    User findUserByEmail(String email);  // Mencari pengguna berdasarkan email

    User findUserById(Long id);  // Mencari pengguna berdasarkan ID
}
