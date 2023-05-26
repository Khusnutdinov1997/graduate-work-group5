package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.model.AdModel;
import com.skypro.adsonline.model.ImageModelAd;
import com.skypro.adsonline.model.ImageModelUser;
import com.skypro.adsonline.model.UserModel;
import com.skypro.adsonline.repository.ImageRepositoryAd;
import com.skypro.adsonline.repository.ImageRepositoryUser;
import com.skypro.adsonline.service.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional

public class ImageServiceImpl implements ImageService {

    private final ImageRepositoryUser imageRepositoryUser;
    private final ImageRepositoryAd imageRepositoryAd;



    public ImageServiceImpl(ImageRepositoryUser imageRepositoryUser, ImageRepositoryAd imageRepositoryAd) {
        this.imageRepositoryUser = imageRepositoryUser;
        this.imageRepositoryAd = imageRepositoryAd;
    }


    @Override
    public void getUserImageFromDisk(HttpServletResponse response, Path filePath, ImageModelUser imageModelUser) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(imageModelUser.getMediaType());
            response.setContentLength((int) imageModelUser.getFileSize());
            is.transferTo(os);
        }
    }

    @Override
    public void getAdImageFromDisk(HttpServletResponse response, Path filePath, ImageModelAd imageModelAd) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(imageModelAd.getMediaType());
            response.setContentLength((int) imageModelAd.getFileSize());
            is.transferTo(os);
        }
    }

    @Override
    public void updateImageUser(UserModel user, MultipartFile image, Path filePath) {
        ImageModelUser userImage = imageRepositoryUser.findByUser(user).orElse(new ImageModelUser());
        userImage.setFilePath(filePath.toString());
        userImage.setFileExtension(getExtension(image.getOriginalFilename()));
        userImage.setFileSize(image.getSize());
        userImage.setMediaType(image.getContentType());
        userImage.setUser(user);
        imageRepositoryUser.save(userImage);


    }

    @Override
    public void updateImageAd(AdModel ad, MultipartFile image, Path filePath) {
        ImageModelAd adImage = imageRepositoryAd.findByAd(ad).orElse(new ImageModelAd());
        adImage.setFilePath(filePath.toString());
        adImage.setFileExtension(getExtension(image.getOriginalFilename()));
        adImage.setFileSize(image.getSize());
        adImage.setMediaType(image.getContentType());
        adImage.setAd(ad);
        imageRepositoryAd.save(adImage);

    }

    @Override
    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    public void saveFileOnDisk(MultipartFile image, Path filePath) throws IOException {
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = image.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);) {
            bis.transferTo(bos);
        }
    }

}
