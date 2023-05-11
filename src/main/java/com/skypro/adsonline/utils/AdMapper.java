package com.skypro.adsonline.utils;

import com.skypro.adsonline.dto.Ads;
import com.skypro.adsonline.dto.FullAds;
import com.skypro.adsonline.dto.CreateAds;
import com.skypro.adsonline.exception.UserNotFoundException;
import com.skypro.adsonline.model.AdModel;
import com.skypro.adsonline.model.UserModel;
import com.skypro.adsonline.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service

public class AdMapper {

    private final UserRepository userRepository;
    public AdMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
}

    /**
     * Dto -> model mapping
     * @param dto CreateAds dto class
     * @return AdModel model class
     */

    public AdModel mapToAdModel(CreateAds dto, MultipartFile image, String username) {

        UserModel user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("User %s not found".formatted(username));
        }

        AdModel model = new AdModel();
        model.setTitle(dto.getTitle());
        model.setDescription(dto.getDescription());
        model.setPrice(dto.getPrice());
        model.setImage(null);
        model.setAuthor(user);
        return model;
    }

    /**
     * Model -> dto mapping
     * @param model input model class
     * @return dto class
     */
    public Ads mapToAdDto(AdModel model){
        Ads dto = new Ads();
        dto.setAuthor(model.getAuthor().getId());
        dto.setImage(model.getImage());
        dto.setPk(model.getId());
        dto.setPrice(model.getPrice());
        dto.setTitle(model.getTitle());
        return dto;
    }

    /**
     * AdModel adModel -> CompleteAds dto mapping
     * @param adModel AdModel model class
     * @return CompleteAds dto class
     */

    public FullAds mapToFullAdsDto(AdModel adModel) {
        FullAds dto = new FullAds();
        dto.setPk(adModel.getId());
        dto.setAuthorFirstName(adModel.getAuthor().getFirstName());
        dto.setAuthorLastName(adModel.getAuthor().getLastName());
        dto.setDescription(adModel.getDescription());
        dto.setEmail(adModel.getAuthor().getUsername());
        dto.setImage(adModel.getImage());
        dto.setPhone(adModel.getAuthor().getPhone());
        dto.setPrice(adModel.getPrice());
        dto.setTitle(adModel.getTitle());
        return dto;
    }

}
