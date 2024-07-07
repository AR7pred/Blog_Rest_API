package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository , PostRepository postRepository,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        //convert DTO to entity
        Comment comment = mapToEntity(commentDto);

        //retrieve post entity by id
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));

        //set post to comment entity
        comment.setPost(post);

        //save comment entity to DB
        Comment newcomment = commentRepository.save(comment);

        //convert entity to DTO
        CommentDto newCommentDto = mapToDto(newcomment);

        return newCommentDto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);

        //convert entity to DTO
        List<CommentDto> commentDtos = comments.stream().map((comment)-> mapToDto(comment)).collect(Collectors.toList());

        return commentDtos;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        //retrieve post by ID
         Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));

         // comment by ID
         Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));

         if(comment.getPost().getId() == post.getId()){
            CommentDto commentDto = mapToDto(comment);
            return commentDto;
         }
         else{
             throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
         }
    }

    @Override
    public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto) {
     Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
     Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));

     if(comment.getPost().getId() == post.getId()){

         comment.setName(commentDto.getName());
         comment.setEmail(commentDto.getEmail());
         comment.setBody(commentDto.getBody());

         Comment updateComment = commentRepository.save(comment);

         CommentDto updatedCommentDto = mapToDto(updateComment);
         return updatedCommentDto;
     }
     else{
         throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
     }
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));

        if(comment.getPost().getId() == post.getId()){
            commentRepository.delete(comment);
        }
        else{
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belongs to post");
        }
    }


//    <---------------------------------------->

    private Comment mapToEntity(CommentDto commentDto) {
          Comment comment = mapper.map(commentDto , Comment.class);

//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());

        return comment;
    }

    private CommentDto mapToDto(Comment comment) {
          CommentDto commentDto = mapper.map(comment, CommentDto.class);

//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }
}
