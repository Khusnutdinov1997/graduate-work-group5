package com.skypro.adsonline.service;

import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.dto.ResponseWrapperComment;
import com.skypro.adsonline.security.SecurityUser;

public interface CommentService {

    ResponseWrapperComment getComments(Integer id);

    Comment addComment(Integer id, Comment comment, SecurityUser currentUser);

    boolean deleteComment(Integer adId, Integer commentId,SecurityUser currentUser);

    Comment updateComment(Integer adId, Integer commentId, Comment comment,SecurityUser currentUser);

}
