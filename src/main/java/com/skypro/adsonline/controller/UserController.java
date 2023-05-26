package com.skypro.adsonline.controller;

import com.skypro.adsonline.dto.NewPassword;
import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.security.SecurityUser;
import com.skypro.adsonline.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("users")


public class UserController {

    private final UserService userService;
    private final UserDetails userDetails;
    
    @Operation(
            summary = "Обновить пароль",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пароль изменен"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "NOT authorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    )
            },
            tags = "Пользователи"
    )
    @PostMapping("/set_password")
    public ResponseEntity<User> setPassword(@RequestBody NewPassword password) {

        User user = userService.setPassword(password, userDetails);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(
            summary = "Информация о зарегистрированном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация получена",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "NOT authorized"
                    )
            },
            tags = "Пользователи"
    )
    @GetMapping("/me")
    public ResponseEntity<User> getUser() {
        User user = userService.getUser(userDetails);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(
            summary = "Обновить зарегистрированного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь изменен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "NOT authorized"
                    )
            },
            tags = "Пользователи"
    )
    @PatchMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody User userN) {
        if (userService.updateUser(userN,userDetails)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(
            summary = "Обновить фото пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Фото изменено"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "NOT authorized"
                    )
            },
            tags = "Пользователи"
    )
    @PatchMapping(value = "/me/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<User> updateUserImage(@RequestPart(name = "image") MultipartFile image) throws IOException {
        User user = userService.updateUserImage(image, userDetails);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}


