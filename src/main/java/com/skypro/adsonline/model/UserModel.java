package com.skypro.adsonline.model;

import com.skypro.adsonline.dto.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")

public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    //@Enumerated(value = EnumType.STRING)
    private Role role;
    private String image;

}
