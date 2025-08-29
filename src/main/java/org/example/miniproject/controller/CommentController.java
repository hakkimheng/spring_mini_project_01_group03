package org.example.miniproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.miniproject.model.dto.request.CommentRequest;
import org.example.miniproject.model.dto.response.ApiResponse;
import org.example.miniproject.model.dto.response.ArticleWithCommentResponse;
import org.example.miniproject.model.dto.response.BaseResponse;
import org.example.miniproject.model.dto.response.CommentResponse;
import org.example.miniproject.model.entity.AppUser;
import org.example.miniproject.model.entity.Comment;
import org.example.miniproject.service.AppUserService;
import org.example.miniproject.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
@Tag(name = "Comment")
class CommentController extends BaseResponse {


    private final CommentService commentService;

    @GetMapping("/{comment-id}")
    @Operation(summary = "Get comment by comment id, can view only your own comment")
    public ResponseEntity<ApiResponse<CommentResponse>> getCommentById(@PathVariable("comment-id") Integer commendId){
        return responseEntity(true,"Get comment successfully",HttpStatus.OK,commentService.getCommentById(commendId).toCommentResponse());
    }

    @PutMapping("/{comment-id}")
    @Operation(summary = "Update comment by comment id, can update only your own comment")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(@PathVariable("comment-id") Integer commentId, @RequestBody CommentRequest commentRequest){
        return responseEntity(true , "Updated comment successfully",HttpStatus.OK,commentService.updateCommentById(commentId , commentRequest).toCommentResponse());
    }

    @DeleteMapping("/{comment-id}")
    @Operation(summary = "Delete comment by comment id, can delete only your own comment")
    public ResponseEntity<ApiResponse<CommentResponse>> deleteCommentById(@PathVariable("comment-id") Integer commentId){
        commentService.deleteComment(commentId);
        return responseEntity(true , "Deleted comment successfully" , HttpStatus.OK);
    }
}
