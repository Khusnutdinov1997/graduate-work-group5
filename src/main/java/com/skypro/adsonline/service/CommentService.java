package com.skypro.adsonline.service;

import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.dto.ResponseWrapperComment;
import org.springframework.security.core.userdetails.UserDetails;


public interface CommentService {

    ResponseWrapperComment getComments(Integer id, UserDetails currentUser);

    Comment addComment(Integer id, Comment comment, UserDetails currentUser);

    boolean deleteComment(Integer adId, Integer commentId,UserDetails currentUser);

    Comment updateComment(Integer adId, Integer commentId, Comment comment,UserDetails currentUser);

}
