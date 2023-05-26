package com.skypro.adsonline.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comments")



public class CommentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "fk_comments_users"))
    private UserModel author;
    @ManyToOne
    @JoinColumn(name = "ad_id", foreignKey = @ForeignKey(name = "fk_comments_ads"))
    private AdModel ad;

    private long creationDateTime;
    private String text;

}
