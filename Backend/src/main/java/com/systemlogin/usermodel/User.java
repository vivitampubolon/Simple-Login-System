package com.systemlogin.usermodel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Primary key dengan auto-increment
    private Long id;

    private String fullName;

    @jakarta.persistence.Column(unique = true)
    private String email;  // Email unik

    private String password;  // Disimpan terenkripsi

    private String role = "ROLE_CUSTOMER";  // Peran default

    private String mobile;  // Nomor ponsel
}
