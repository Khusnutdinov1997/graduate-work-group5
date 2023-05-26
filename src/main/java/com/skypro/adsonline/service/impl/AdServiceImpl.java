package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.*;
import com.skypro.adsonline.exception.AdNotFoundException;
import com.skypro.adsonline.model.AdModel;
import com.skypro.adsonline.model.UserModel;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.security.SecurityUser;
import com.skypro.adsonline.service.AdService;
import com.skypro.adsonline.service.ImageService;
import com.skypro.adsonline.service.UserService;
import com.skypro.adsonline.utils.AdMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Transactional
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final ImageService imageService;
    private final UserService userService;

    @Value("${ads.image.dir.path}")
    private String imageAdsDir;

    public AdServiceImpl(AdRepository adRepository, AdMapper adMapper, ImageService imageService, UserService userService) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
        this.imageService = imageService;
        this.userService = userService;
    }

    @Override
    public Ads addAd(CreateAds properties, MultipartFile image, UserDetails currentUser) throws IOException {
        AdModel adModel = adMapper.mapToAdModel(properties, currentUser.getUsername());
        adModel= adRepository.save(adModel);
        Path filePath = getFilePath(adModel, image);
        imageService.saveFileOnDisk(image, filePath);
        imageService.updateImageAd(adModel, image, filePath);

        adModel.setImage("/" + imageAdsDir + "/" + adModel.getId());
        adRepository.save(adModel);

        return adMapper.mapToAdDto(adModel);
    }

    @Override
    public FullAds getAds(Integer id,UserDetails currentUser) {
        AdModel adModel = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Advertisement %s not found".formatted(id)));
        return adMapper.mapToFullAdsDto(adModel);
    }

    @Override
    public boolean removeAd(Integer id,UserDetails currentUser) throws IOException {
        checkAccess(id,currentUser);
        AdModel adModel = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Advertisement %s not found".formatted(id)));
        Files.deleteIfExists(Path.of(adModel.getAdImage().getFilePath()));
        adRepository.delete(adModel);
        return true;
    }

    @Override
    public Ads updateAds(Integer id, CreateAds ads,UserDetails currentUser) {
        checkAccess(id,currentUser);
        AdModel adModel = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Advertisement %s not found".formatted(id)));

        adModel.setTitle(ads.getTitle());
        adModel.setDescription(ads.getDescription());
        adModel.setPrice(ads.getPrice());

        adRepository.save(adModel);
        return adMapper.mapToAdDto(adModel);
    }


    @Override
    public ResponseWrapperAds getAdsMe(UserDetails currentUser) {
        UserModel author = userService.checkUserByUsername(currentUser.getUsername());
        List<Ads> ads = adRepository.findByAuthor(author).stream()
            .map(adMapper::mapToAdDto)
            .toList();
        return new ResponseWrapperAds(ads.size(), ads);
    }

    @Override
    public ResponseWrapperAds getAllAds() {
        List<Ads> ads = adRepository.findAll().stream()
                .map(adMapper::mapToAdDto)
                .toList();
        return new ResponseWrapperAds(ads.size(), ads);
    }

    @Override
    public boolean updateImage(Integer id, MultipartFile image, UserDetails currentUser) throws IOException {
        checkAccess(id, currentUser);

        AdModel adModel = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Advertisement %s not found".formatted(id)));

        Path filePath = getFilePath(adModel, image);
        imageService.saveFileOnDisk(image, filePath);
        imageService.updateImageAd(adModel, image, filePath);

        adModel.setImage("/" + imageAdsDir + "/" + adModel.getId());
        adRepository.save(adModel);

        return true;
    }

    @Override
    public ResponseWrapperAds getAdsByTitleMatch(String title) {
        List<Ads> ads = adRepository.findAdModelByTitleContainingIgnoreCase(title).stream()
                .map(adMapper::mapToAdDto)
                .toList();
        return new ResponseWrapperAds(ads.size(), ads);
    }

    /**
     * Author of advertisement should be the same as logged user.
     * If ad-author does not match logged user OR the user is not ADMIN then access is denied.
     * @param id ad id
     * @param currentUser logged user
     */

    public void checkAccess(Integer id, UserDetails currentUser) {
        String authorUser = adRepository
                .findById(id)
                .orElseThrow(() -> new AdNotFoundException("Advertisement %s not found".formatted(id)))
                .getAuthor()
                .getUsername();
        if (!authorUser.equals(currentUser.getUsername())
                || !currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.name()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied!".formatted(currentUser.getUsername()));
        }
    }

    private Path getFilePath(AdModel ad, MultipartFile image) {
        return Path.of(imageAdsDir, ad.getAuthor().getId() + "-" + ad.getId() + "." + imageService.getExtension(image.getOriginalFilename()));
    }
}
