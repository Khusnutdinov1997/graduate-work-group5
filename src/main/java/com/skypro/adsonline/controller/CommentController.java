package com.skypro.adsonline.controller;

import com.skypro.adsonline.dto.Comment;
import com.skypro.adsonline.dto.ResponseWrapperComment;
import com.skypro.adsonline.security.SecurityUser;
import com.skypro.adsonline.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("ads")


public class CommentController {


    private final CommentService commentsService;
    private final UserDetails userDetails;

    @Operation(
            summary = "Получить ответ на объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperComment.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "NOT authorized"
                    )
            },
            tags = "Комментарии"
    )
    @GetMapping("{id}/comment")
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable Integer id) {
        ResponseWrapperComment comment = commentsService.getComments(id,userDetails);
        if(comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Добавить комментарий к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Comment.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "NOT authorized"
                    )
            },
            tags = "Комментарии"
    )
    @PostMapping("/{id}/comment")
    public ResponseEntity<Comment> addComment(@PathVariable Integer id,
                                            @RequestBody Comment comment) {

        Comment newComment = commentsService.addComment(id, comment,userDetails);

        if(newComment != null) {
            return ResponseEntity.ok(newComment);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Удалить комментарий к обьявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "NOT authorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    )
            },
            tags = "Комментарии"
    )
    @DeleteMapping("/{adId}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer adId,
                                           @PathVariable Integer commentId) {
        if(commentsService.deleteComment(adId, commentId, userDetails)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Обновить комментарий к обьявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Comment.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "NOT authorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    )
            },
            tags = "Комментарии"
    )
    @PatchMapping("/{adId}/comment/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable Integer adId,
            @PathVariable Integer commentId,
            @RequestBody Comment comment) {

        Comment updatedComment = commentsService.updateComment(adId, commentId, comment,userDetails);
        if(updatedComment != null) {
            return ResponseEntity.ok(updatedComment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
