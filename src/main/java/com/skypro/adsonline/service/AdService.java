package com.skypro.adsonline.service;

import com.skypro.adsonline.dto.Ads;
import com.skypro.adsonline.dto.FullAds;
import com.skypro.adsonline.dto.CreateAds;
import com.skypro.adsonline.dto.ResponseWrapperAds;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface AdService {

    Ads addAd(CreateAds properties, MultipartFile image, UserDetails currentUser) throws IOException;

    FullAds getAds(Integer id,UserDetails currentUser);

    boolean removeAd(Integer id,UserDetails currentUser)throws IOException;

    Ads updateAds(Integer id, CreateAds ads,UserDetails currentUser);

    ResponseWrapperAds getAdsMe(UserDetails currentUser);

    ResponseWrapperAds getAllAds();

    boolean updateImage(Integer id, MultipartFile image,UserDetails currentUser) throws IOException;

    ResponseWrapperAds getAdsByTitleMatch(String title);

    void checkAccess(Integer id, UserDetails currentUser);
}
