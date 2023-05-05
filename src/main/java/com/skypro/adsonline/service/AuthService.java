package com.skypro.adsonline.service;
import com.skypro.adsonline.dto.RegisterReq;
import com.skypro.adsonline.dto.Role;
import com.skypro.adsonline.dto.RegisterReq;
import com.skypro.adsonline.dto.Role;


public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}