package ru.skypro.homework.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.dto.UserImages;

@RestController()
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {
    @PatchMapping("/me")
    public UserDTO updateUser(@RequestBody UserDTO user) {
        return new UserDTO();
    }

    @GetMapping("/me/image")
    public UserImages updateUserImage(@RequestBody UserImages userImage) {
        return new UserImages();
    }


}
