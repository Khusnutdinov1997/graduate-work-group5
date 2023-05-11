package com.skypro.adsonline.dto;

import lombok.Data;

@Data

public class FullAds {

    private int pk; // id объявления
    private String authorFirstName; // имя автора объявления
    private String authorLastName; // фамилия автора объявления
    private String description; // описание объявления
    private String email; // е-мейл автора объявления
    private String image; // ссылка на аватарку объявления
    private String phone; // телефон автора объявления
    private int price; // цена объявления
    private String title; // заголовок объявления

}
