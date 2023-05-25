package com.skypro.adsonline.service;

import com.skypro.adsonline.model.AdModel;
import com.skypro.adsonline.model.ImageModelAd;
import com.skypro.adsonline.model.ImageModelUser;
import com.skypro.adsonline.model.UserModel;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface ImageService {

    void getUserImageFromDisk(HttpServletResponse response, Path filePath, ImageModelUser imageModelUser) throws IOException;
    void getAdImageFromDisk(HttpServletResponse response, Path filePath, ImageModelAd imageModelAd) throws IOException;

    void updateImageUser(UserModel user, MultipartFile image, Path filePath);
    void updateImageAd(AdModel ad, MultipartFile image, Path filePath);
    String getExtension(String fileName);
    void saveFileOnDisk(MultipartFile image, Path filePath) throws IOException;
}
