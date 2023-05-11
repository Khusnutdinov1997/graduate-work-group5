package com.skypro.adsonline.model;


    import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


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
        private String image;
    }


