package com.skypro.adsonline.repository;

import com.skypro.adsonline.model.AdModel;
import com.skypro.adsonline.model.ImageModelAd;
import com.skypro.adsonline.model.ImageModelUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepositoryAd extends JpaRepository<ImageModelAd, Integer> {
    Optional<ImageModelAd> findByAd(AdModel ad);
}



