package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.dto.ResponseWrapperComment;
import com.skypro.adsonline.exception.AdNotFoundException;
import com.skypro.adsonline.exception.CommentNotFoundException;
import com.skypro.adsonline.model.AdModel;
import com.skypro.adsonline.model.CommentModel;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.repository.CommentRepository;
import com.skypro.adsonline.security.SecurityUser;
import com.skypro.adsonline.service.CommentService;
import com.skypro.adsonline.utils.CommentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CommentServiceImpl implements CommentService {

    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(AdRepository adRepository, CommentRepository commentRepository, CommentMapper commentMapper) {
        this.adRepository = adRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }


    @Override
    public ResponseWrapperComment getComments(Integer id) {
        List<Comment> comments = commentRepository.findByAdId(id).stream()
                .map(commentMapper::mapToCommentDto)
                .toList();
        return new ResponseWrapperComment(comments.size(), comments);
    }

    @Override
    public Comment addComment(Integer id, Comment comment, SecurityUser currentUser) {
        AdModel adModel = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Advertisement %s not found".formatted(id)));

        CommentModel commentModel = new CommentModel();
        commentModel.setAuthor(currentUser.getUser());
        commentModel.setAd(adModel);
        commentModel.setCreationDateTime(System.currentTimeMillis());
        commentModel.setText(comment.getText());

        commentRepository.save(commentModel);

        return commentMapper.mapToCommentDto(commentModel);
    }

    @Override
    public boolean deleteComment(Integer adId, Integer commentId) {
        CommentModel commentModel = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Comment %s not found".formatted(commentId)));
        commentRepository.delete(commentModel);
        return true;
    }

    @Override
    public Comment updateComment(Integer adId, Integer commentId, Comment comment) {
        AdModel adModel = adRepository.findById(adId).orElseThrow(() -> new AdNotFoundException("Advertisement %s not found".formatted(adId)));
        CommentModel foundReply = commentRepository.findById(commentId).
                orElseThrow(() -> new CommentNotFoundException("Comment %s not found".formatted(commentId)));
        foundReply.setText(comment.getText());
        commentRepository.save(foundReply);
        return commentMapper.mapToCommentDto(foundReply);
    }

}
