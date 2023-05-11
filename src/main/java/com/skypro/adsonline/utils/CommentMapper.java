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
     * Dto -> model mapping
     * @param advId advertesement id
     * @param dto input dto class
     * @return model class
     */
    public CommentModel mapToCommentModel(Integer advId, Comment dto) {
        UserModel user = userRepository.findById(dto.getAuthor()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        AdModel adModel = adRepository.findById(advId).orElse(null);
        if (adModel == null) {
            throw new AdNotFoundException("Advertisement not found");
        }
        CommentModel commentModel = new CommentModel();
        commentModel.setAuthor(user);
        commentModel.setAd(adModel);
        commentModel.setCreationDateTime(dto.getCreationDateTime());
        commentModel.setText(dto.getText());
        return commentModel;
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

