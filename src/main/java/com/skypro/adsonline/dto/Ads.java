package com.skypro.adsonline.dto;

import lombok.Data;

@Data

public class Ads {

    private int author; // id автора объявления
    private String image; // ссылка на аватарку объявления
    private int pk; // id самого объявления
    private int price; // цена объявления
    private String title; // заголовок объявления

}
