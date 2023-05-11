package com.skypro.adsonline.repository;

import com.skypro.adsonline.model.AdModel;
import com.skypro.adsonline.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdRepository extends JpaRepository<AdModel, Integer> {

    List<AdModel> findByAuthor(UserModel author);

    List<AdModel> findAdModelByTitleContainingIgnoreCase(String title);

    }
