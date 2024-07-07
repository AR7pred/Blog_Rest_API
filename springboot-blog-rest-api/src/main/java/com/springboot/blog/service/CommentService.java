package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    CommentDto createComment(long postId,CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(long postId,long commentId);

    CommentDto updateCommentById(long postId,long commentId,CommentDto commentDto);

    void deleteCommentById(long postId,long commentId);
}
