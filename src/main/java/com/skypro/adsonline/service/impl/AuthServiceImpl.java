package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.RegisterReq;
import com.skypro.adsonline.dto.Role;
import com.skypro.adsonline.model.UserModel;
import com.skypro.adsonline.repository.UserRepository;
import com.skypro.adsonline.security.JpaUserDetailsService;
import com.skypro.adsonline.service.AuthService;
import com.skypro.adsonline.utils.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;

    private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);


    public AuthServiceImpl(UserRepository userRepository, UserMapper mappingUtils, UserDetailsService userDetailsService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userMapper = mappingUtils;
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;

    }
    @Override
    public boolean login(String username, String password) {
        UserDetails foundUser = userDetailsService.loadUserByUsername(username);
        String encryptedPassword = foundUser.getPassword();
        return encoder.matches(password, encryptedPassword);

    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        try {
            registerReq.setRole(role);
            saveUser(registerReq);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * For registering users - saving during registration.
     * User mapper is used when mapping to User Model .
     * @param registerReq from registration
     */

    public UserModel saveUser(RegisterReq registerReq) {
        UserModel user = userMapper.mapToUserModel(registerReq);
        user.setPassword(encoder.encode(registerReq.getPassword()));
        return userRepository.save(user);
    }

}
