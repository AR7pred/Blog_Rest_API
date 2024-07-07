package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //post commments on post by postID
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId, @RequestBody CommentDto commentDto) {
        CommentDto commentDto1= commentService.createComment(postId,commentDto);
        return new ResponseEntity<>(commentDto1, HttpStatus.CREATED);
    }

    //get all comments by postID
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable long postId) {
        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    //get comment of a post by ID
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentofPostById(@PathVariable long postId, @PathVariable long commentId) {
        CommentDto commentDto= commentService.getCommentById(postId,commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.FOUND);
    }

    //update comment of a post by ID
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable long postId, @PathVariable long commentId, @RequestBody CommentDto commentDto) {
       CommentDto commentDto1=  commentService.updateCommentById(postId,commentId,commentDto);
        return new ResponseEntity<>(commentDto1, HttpStatus.OK);
    }

    //delete comment by post By ID
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long postId, @PathVariable long commentId) {
        commentService.deleteCommentById(postId,commentId);
        return new ResponseEntity<>("Comment deleted Successfully", HttpStatus.OK);
    }
}
