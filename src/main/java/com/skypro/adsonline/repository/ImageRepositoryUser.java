package com.skypro.adsonline.repository;

import com.skypro.adsonline.model.ImageModelUser;
import com.skypro.adsonline.model.UserModel;
import com.skypro.adsonline.model.ImageModelAd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepositoryUser extends JpaRepository<ImageModelUser, Integer> {

   Optional<ImageModelUser> findByUser(UserModel user);

}
