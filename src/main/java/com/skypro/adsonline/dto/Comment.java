package com.skypro.adsonline.dto;

import lombok.Data;

@Data

public class Comment {

    private int author; // id автора комментария
    private String authorImage; // ссылка на аватар автора комментария
    private String authorName; // имя автора комментария
    private long creationDateTime; // дата и время создания комментария
    private int pk; // id комментария
    private String text; // текст комментария

}
