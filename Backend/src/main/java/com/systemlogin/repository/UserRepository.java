package com.systemlogin.repository;

import com.systemlogin.usermodel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);  // Mencari user berdasarkan email
}
