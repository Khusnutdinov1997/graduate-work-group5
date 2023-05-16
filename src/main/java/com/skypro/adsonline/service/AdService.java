package com.skypro.adsonline.service;

import com.skypro.adsonline.dto.Ads;
import com.skypro.adsonline.dto.FullAds;
import com.skypro.adsonline.dto.CreateAds;
import com.skypro.adsonline.dto.ResponseWrapperAds;
import com.skypro.adsonline.security.SecurityUser;
import org.springframework.web.multipart.MultipartFile;


public interface AdService {

    Ads addAd(CreateAds properties, MultipartFile image, SecurityUser currentUser);

    FullAds getAds(Integer id);

    boolean removeAd(Integer id,SecurityUser currentUser);

    Ads updateAds(Integer id, CreateAds ads,SecurityUser currentUser);

    ResponseWrapperAds getAdsMe(SecurityUser currentUser);

    ResponseWrapperAds getAllAds();

    boolean updateImage(Integer id, MultipartFile image,SecurityUser currentUser);

    ResponseWrapperAds getAdsByTitleMatch(String title);
}
