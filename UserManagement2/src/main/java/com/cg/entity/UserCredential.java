package com.cg.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//User credentilas entity
//@Entity
//@Table(name = "userlogin")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class UserCredential {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @NotBlank(message = "Username must not be blank")
//    private String username;
//
//    @NotBlank(message = "Email must not be blank")
//    @Email(message = "Email should be valid")
//    private String email;
//
//    @NotBlank(message = "Password must not be blank")
//    @Size(min = 6, message = "Password must be at least 6 characters long")
//    private String password;
//
//    @NotBlank(message = "Role must not be blank")
//    @Pattern(regexp = "^(user|admin|washer)$", message = "Role must be either 'user','admin','washer'")
//    private String role;
//}


@Entity
@Table(name = "userlogin", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Status must not be blank")
    private String status;

    @NotBlank(message = "Address must not be blank")
    private String address;

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Role must not be blank")
    @Pattern(regexp = "^(user|washer|admin)$", message = "Role must be either 'user','washer'")
    private String role;
}
