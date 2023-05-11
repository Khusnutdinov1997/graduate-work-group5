package com.skypro.adsonline.service;

import com.skypro.adsonline.dto.NewPassword;
import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.model.UserModel;
import com.skypro.adsonline.security.SecurityUser;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User setPassword(NewPassword newPasswordDto, SecurityUser currentUser);
    User getUser(SecurityUser currentUser);
    boolean updateUser(User userDto, SecurityUser currentUser);
    boolean updateUserImage(MultipartFile image);
    UserModel checkUserByUsername(String username);
}
