package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.NewPassword;
import com.skypro.adsonline.dto.User;
import com.skypro.adsonline.exception.UserNotFoundException;
import com.skypro.adsonline.exception.WrongPasswordException;
import com.skypro.adsonline.model.UserModel;
import com.skypro.adsonline.repository.UserRepository;
import com.skypro.adsonline.service.ImageService;
import com.skypro.adsonline.service.UserService;
import com.skypro.adsonline.utils.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.util.Optional;

@Service
@Transactional

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final ImageService imageService;

    @Value("${users.image.dir.path}")
    private String imageUserDir;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, ImageService imageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.imageService = imageService;
    }

    @Override
    public User getUser(UserDetails currentUser) {
        UserModel user = userRepository.findByUsername(currentUser.getUsername());
        if (user != null) {
            return userMapper.mapToUserDto(user);
        } else {
            return null;
        }

    }

    @Override
    public boolean updateUser(User userN, UserDetails currentUser) {
        UserModel user = checkUserByUsername(currentUser.getUsername());

        user.setFirstName(userN.getFirstName());
        user.setLastName(userN.getLastName());
        user.setPhone(userN.getPhone());
        userRepository.save(user);
        return true;
    }

    @Override
    public User updateUserImage(MultipartFile image,UserDetails currentUser) throws IOException {

        UserModel user = checkUserByUsername(currentUser.getUsername());

        Path filePath = Path.of(imageUserDir, user.getId() + "." + imageService.getExtension(image.getOriginalFilename()));
        imageService.saveFileOnDisk(image, filePath);
        imageService.updateImageUser(user, image, filePath);

        user.setImage("/" + imageUserDir + "/" + user.getId());
        userRepository.save(user);

        return userMapper.mapToUserDto(user);

    }

    public User setPassword(NewPassword newPasswordDto, UserDetails currentUser) {
        UserModel user = checkUserByUsername(currentUser.getUsername());
        String encryptedPassword = user.getPassword();
        if (!passwordEncoder.matches(newPasswordDto.getCurrentPassword(), encryptedPassword)) {
            throw new WrongPasswordException("Password %s is not correct".formatted(currentUser.getUsername()));
        }

        String newPassword = newPasswordDto.getNewPassword();

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return userMapper.mapToUserDto(user);

    }

    @Override
    public UserModel checkUserByUsername(String username) {
        UserModel user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User %s not found!".formatted(username));
        }
        return user;
    }

}
