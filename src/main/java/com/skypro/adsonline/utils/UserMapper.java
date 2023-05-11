package com.skypro.adsonline.utils;

import com.skypro.adsonline.dto.RegisterReq;
import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.model.UserModel;
import org.springframework.stereotype.Service;

@Service

public class UserMapper {
    /**
     * Dto -> model mapping
     * @param dto input dto class
     * @return userModel class
     */
    public UserModel mapToUserModel(RegisterReq dto){
        UserModel userModel = new UserModel();
        userModel.setUsername(dto.getUsername());
        userModel.setPassword(dto.getPassword());
        userModel.setFirstName(dto.getFirstName());
        userModel.setLastName(dto.getLastName());
        userModel.setPhone(dto.getPhone());
        userModel.setRole(dto.getRole());
        return userModel;
    }

    /**
     * Model -> dto mapping
     * @param userModel input model class
     * @return dto class
     */
    public User mapToUserDto(UserModel userModel) {
        User dto = new User();
        dto.setId(userModel.getId());
        dto.setEmail(userModel.getUsername());
        dto.setFirstName(userModel.getFirstName());
        dto.setLastName(userModel.getLastName());
        dto.setPhone(userModel.getPhone());
        dto.setImage(userModel.getImage());
        return dto;
    }
}
