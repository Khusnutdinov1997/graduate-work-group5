package com.skypro.adsonline.service.impl;

import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.dto.ResponseWrapperComment;
import com.skypro.adsonline.dto.Role;
import com.skypro.adsonline.exception.AdNotFoundException;
import com.skypro.adsonline.exception.CommentNotFoundException;
import com.skypro.adsonline.model.AdModel;
import com.skypro.adsonline.model.CommentModel;
import com.skypro.adsonline.model.UserModel;
import com.skypro.adsonline.repository.AdRepository;
import com.skypro.adsonline.repository.CommentRepository;
import com.skypro.adsonline.service.CommentService;
import com.skypro.adsonline.service.UserService;
import com.skypro.adsonline.utils.CommentMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional

public class CommentServiceImpl implements CommentService {

    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;

    public CommentServiceImpl(AdRepository adRepository, CommentRepository commentRepository, CommentMapper commentMapper, UserService userService) {
        this.adRepository = adRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userService = userService;
    }


    @Override
    public ResponseWrapperComment getComments(Integer id, UserDetails currentUser) {
        List<Comment> comments = commentRepository.findByAdId(id).stream()
                .map(commentMapper::mapToCommentDto)
                .toList();
        return new ResponseWrapperComment(comments.size(), comments);
    }

    @Override
    public Comment addComment(Integer id, Comment comment, UserDetails currentUser) {
        AdModel adModel = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException("Advertisement %s not found".formatted(id)));
        UserModel author = userService.checkUserByUsername(currentUser.getUsername());

        CommentModel commentModel = new CommentModel();
        commentModel.setAuthor(author);
        commentModel.setAd(adModel);
        commentModel.setCreationDateTime(System.currentTimeMillis());
        commentModel.setText(comment.getText());

        commentRepository.save(commentModel);

        return commentMapper.mapToCommentDto(commentModel);
    }

    @Override
    public boolean deleteComment(Integer adId, Integer commentId,UserDetails currentUser) {
        checkAccess(commentId, currentUser);
        CommentModel commentModel = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Comment %s not found".formatted(commentId)));
        commentRepository.delete(commentModel);
        return true;
    }

    @Override
    public Comment updateComment(Integer adId, Integer commentId, Comment comment,UserDetails currentUser) {
        checkAccess(commentId, currentUser);
        AdModel adModel = adRepository.findById(adId).orElseThrow(() -> new AdNotFoundException("Advertisement %s not found".formatted(adId)));
        CommentModel foundComment = commentRepository.findById(commentId).
                orElseThrow(() -> new CommentNotFoundException("Comment %s not found".formatted(commentId)));
        foundComment.setText(comment.getText());
        commentRepository.save(foundComment);
        return commentMapper.mapToCommentDto(foundComment);
    }

    /**
     * Author of the comment should be the same as logged user.
     * If author does not match the logged user OR he is not an ADMIN then access is denied.
     * @param commentId comment id
     * @param currentUser logged user
     */
    private void checkAccess(Integer commentId, UserDetails currentUser) {
        String authorUser = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment %s not found".formatted(commentId)))
                .getAuthor()
                .getUsername();
        if (!authorUser.equals(currentUser.getUsername())
                || !currentUser.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.name()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied!".formatted(currentUser.getUsername()));
        }
    }

}
