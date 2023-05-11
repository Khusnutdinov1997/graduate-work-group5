package com.skypro.adsonline.security;

import com.skypro.adsonline.dto.RegisterReq;
import com.skypro.adsonline.exception.UserNotFoundException;
import com.skypro.adsonline.model.UserModel;
import com.skypro.adsonline.repository.UserRepository;
import com.skypro.adsonline.utils.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public JpaUserDetailsService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserModel userFromDb = userRepository.findByUsername(username);
        if (userFromDb == null) {
            throw new UserNotFoundException("User %s not found".formatted(username));
        }
        return new SecurityUser(userFromDb);
    }

    public UserModel saveUser(RegisterReq registerReq) {
        UserModel user = userMapper.mapToUserModel(registerReq);
        user.setPassword(encoder.encode(registerReq.getPassword()));
        return userRepository.save(user);
    }
}
