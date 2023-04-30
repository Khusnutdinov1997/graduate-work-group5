package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class UserDTO {
    String phone;
    String lastName;
    String firstName;
    String email;
    Integer id;
}
