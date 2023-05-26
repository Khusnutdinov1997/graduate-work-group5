package com.skypro.adsonline.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ads_images")


public class ImageModelAd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String filePath;
    private String fileExtension;
    private long fileSize;
    private String mediaType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "ad_id", foreignKey = @ForeignKey(name = "fk_ads_images_ads"))
    private AdModel ad;

   }
