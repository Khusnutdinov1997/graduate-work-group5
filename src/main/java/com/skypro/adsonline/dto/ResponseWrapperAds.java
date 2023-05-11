package com.skypro.adsonline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

public class ResponseWrapperAds {

    private int count; // итого объявлений
    private List<Ads> results;
}
