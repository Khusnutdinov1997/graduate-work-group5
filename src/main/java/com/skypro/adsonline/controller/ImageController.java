package com.skypro.adsonline.controller;


import com.skypro.adsonline.exception.AdNotFoundException;
import com.skypro.adsonline.exception.UserNotFoundException;
import com.skypro.adsonline.model.AdModel;
import com.skypro.adsonline.model.ImageModelAd;
import com.skypro.adsonline.model.ImageModelUser;
import com.skypro.adsonline.model.UserModel;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.repository.ImageRepositoryAd;
import com.skypro.adsonline.repository.ImageRepositoryUser;
import com.skypro.adsonline.repository.UserRepository;
import com.skypro.adsonline.service.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@Transactional

public class ImageController {

    private final UserRepository userRepository;
    private final ImageService imageService;
    private final ImageRepositoryAd imageRepositoryAd;
    private final ImageRepositoryUser imageRepositoryUser;
    private final AdRepository adRepository;

    @Value("${users.image.dir.path}")
    private String imageUserDir;

    @Value("${ads.image.dir.path}")
    private String imageAdsDir;

    public ImageController(UserRepository userRepository, ImageService imageService, ImageRepositoryAd imageRepositoryAd, ImageRepositoryUser imageRepositoryUser, AdRepository adRepository) {
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.imageRepositoryAd = imageRepositoryAd;
        this.imageRepositoryUser = imageRepositoryUser;
        this.adRepository = adRepository;
    }


    @GetMapping("/users_images/{userId}")
    public void getUserImageFromDisk(@PathVariable Integer userId, HttpServletResponse response) throws IOException {
        UserModel user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User %s not found!".formatted(userId)));

        Optional<ImageModelUser> userImage = imageRepositoryUser.findByUser(user);
        if (userImage.isEmpty()) {
            return;
        }

        Path filePath = Path.of(imageUserDir, userId + "." + userImage.get().getFileExtension());
        imageService.getUserImageFromDisk(response, filePath, userImage.get());
    }


    @GetMapping("/ads_images/{adId}")
    public void getAdImageFromDisk(@PathVariable Integer adId, HttpServletResponse response) throws IOException {
        AdModel ad = adRepository.findById(adId).orElseThrow(() -> new AdNotFoundException("Advertisement %s not found!".formatted(adId)));

        Optional<ImageModelAd> adImage = imageRepositoryAd.findByAd(ad);
        if (adImage.isEmpty()) {
            return;
        }

        Path filePath = Path.of(imageAdsDir, ad.getAuthor().getId() + "-" + ad.getId() + "." + adImage.get().getFileExtension());
        imageService.getAdImageFromDisk(response, filePath, adImage.get());
    }

}
