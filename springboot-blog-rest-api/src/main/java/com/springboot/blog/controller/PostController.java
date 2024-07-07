package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;
    private PostService postService;

    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    //create blog post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto newPostDto = postService.createPost(postDto);
        return new ResponseEntity<>(newPostDto, HttpStatus.CREATED);
    }

    //get ALL posts
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue= AppConstants.DEFAULT_PAGE_SIZE , required = false) int pageSize,
            @RequestParam(value = "sort" , defaultValue=AppConstants.DEFAULT_SORT_BY ,required = false) String sortBy,
            @RequestParam(value = "sortDir" , defaultValue=AppConstants.DEFAULT_SORT_DIRECTION , required = false) String sortDirection
    ) {
        PostResponse postDto = postService.getAllPosts(pageNo,pageSize,sortBy,sortDirection);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    //get post by ID
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
         PostDto postDto = postService.getPostById(id);
         return new ResponseEntity<>(postDto, HttpStatus.FOUND);
    }

    //update post by ID
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id , @RequestBody PostDto postDto) {
        PostDto updatedPost = postService.updatePost(id, postDto);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "Deleted post with id: " + id;
    }
}
