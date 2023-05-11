package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.Ads;
import com.skypro.adsonline.dto.FullAds;
import com.skypro.adsonline.dto.CreateAds;
import com.skypro.adsonline.dto.ResponseWrapperAds;
import com.skypro.adsonline.exception.AdNotFoundException;
import com.skypro.adsonline.model.AdModel;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.security.SecurityUser;
import com.skypro.adsonline.service.AdService;
import com.skypro.adsonline.utils.AdMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;

    public AdServiceImpl(AdRepository adRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
    }


    @Override
    public Ads addAd(CreateAds properties, MultipartFile image, SecurityUser currentUser) {
        AdModel adModel = adMapper.mapToAdModel(properties, image, currentUser.getUsername());
        adRepository.save(adModel);
        return adMapper.mapToAdDto(adModel);
    }

    @Override
    public FullAds getAds(Integer id) {
        AdModel adModel = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Advertisement %s not found".formatted(id)));
        return adMapper.mapToFullAdsDto(adModel);
    }

    @Override
    public boolean removeAd(Integer id) {
        AdModel adModel = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Advertisement %s not found".formatted(id)));
        adRepository.delete(adModel);
        return true;
    }

    @Override
    public Ads updateAds(Integer id, CreateAds ads) {
        AdModel adModel = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Advertisement %s not found".formatted(id)));

        adModel.setTitle(ads.getTitle());
        adModel.setDescription(ads.getDescription());
        adModel.setPrice(ads.getPrice());

        adRepository.save(adModel);
        return adMapper.mapToAdDto(adModel);
    }


    @Override
    public ResponseWrapperAds getAdsMe(SecurityUser currentUser) {
        List<Ads> ads = adRepository.findByAuthor(currentUser.getUser()).stream()
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
    public boolean updateImage(Integer id, MultipartFile image) {
        return false;
    }

    @Override
    public ResponseWrapperAds getAdsByTitleMatch(String title) {
        List<Ads> ads = adRepository.findAdModelByTitleContainingIgnoreCase(title).stream()
                .map(adMapper::mapToAdDto)
                .toList();
        return new ResponseWrapperAds(ads.size(), ads);
    }
}
