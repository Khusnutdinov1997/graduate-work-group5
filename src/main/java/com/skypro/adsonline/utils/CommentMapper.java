package com.skypro.adsonline.utils;

import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.exception.AdNotFoundException;
import com.skypro.adsonline.exception.UserNotFoundException;
import com.skypro.adsonline.model.AdModel;
import com.skypro.adsonline.model.CommentModel;
import com.skypro.adsonline.model.UserModel;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service

public class CommentMapper {

    private final UserRepository userRepository;
    private final AdRepository adRepository;


    public CommentMapper(UserRepository userRepository, AdRepository adRepository) {
        this.userRepository = userRepository;
        this.adRepository = adRepository;
    }

    /**
     * Model -> dto mapping
     * @param commentModel input model class
     * @return dto class
     */
    public Comment mapToCommentDto(CommentModel commentModel) {
        Comment dto = new Comment();
        dto.setAuthor(commentModel.getAuthor().getId());
        dto.setAuthorImage(commentModel.getAuthor().getImage());
        dto.setAuthorName(commentModel.getAuthor().getUsername());
        dto.setCreationDateTime(commentModel.getCreationDateTime());
        dto.setPk(commentModel.getId());
        dto.setText(commentModel.getText());
        return dto;
    }

}

