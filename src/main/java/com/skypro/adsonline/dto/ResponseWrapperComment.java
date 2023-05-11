package com.skypro.adsonline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

public class ResponseWrapperComment {

    private int count; // общее количество ответов
    private List<Comment> results;

}
