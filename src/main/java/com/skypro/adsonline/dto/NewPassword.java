package com.skypro.adsonline.dto;

import lombok.Data;

@Data

public class NewPassword {

        private String currentPassword; // текущий пароль
        private String newPassword; // новый пароль
}
