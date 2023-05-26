package com.skypro.adsonline.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;


@Entity
    @Getter
    @Setter
    @Table(name = "ads")

    public class AdModel {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String title;
        private String description;
        private int price;

        @ManyToOne
        @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "fk_ads_users"))
        private UserModel author;

        @OneToMany(mappedBy = "ad", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        private Collection<CommentModel> comments;

        @OneToOne(mappedBy = "ad", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        private ImageModelAd adImage;

        private String image;
    }


