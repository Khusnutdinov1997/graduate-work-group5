package com.skypro.adsonline.dto;

import lombok.Data;

@Data
public class LoginReq {
    private String password; // пароль
    private String username; // логин

}