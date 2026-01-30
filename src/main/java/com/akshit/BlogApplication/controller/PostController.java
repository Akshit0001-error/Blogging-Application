package com.akshit.BlogApplication.controller;

import com.akshit.BlogApplication.domain.CreatePostRequest;
import com.akshit.BlogApplication.domain.UpdatePostRequest;
import com.akshit.BlogApplication.domain.dto.CreatePostRequestDto;
import com.akshit.BlogApplication.domain.dto.PostDto;
import com.akshit.BlogApplication.domain.dto.UpdatePostRequestDto;
import com.akshit.BlogApplication.domain.entity.Post;
import com.akshit.BlogApplication.domain.entity.User;
import com.akshit.BlogApplication.mapper.PostMapper;
import com.akshit.BlogApplication.service.PostService;
import com.akshit.BlogApplication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false)UUID categoryId,
            @RequestParam(required = false)UUID tagID){
        List<Post> posts = postService.getAllPosts(categoryId, tagID);
        List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDtos);
    }

    @GetMapping(path = "/draft")
    public ResponseEntity<List<PostDto>> getDrafts(@RequestParam UUID userId){
        User loggedInUser = userService.getUserByID(userId);
        List<Post> draftPosts = postService.getDraftPosts(loggedInUser);
        List<PostDto> postDtos = draftPosts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @RequestBody CreatePostRequestDto createPostRequestDto,
            @Valid @RequestAttribute UUID userId)
    {
        User loggedInUser = userService.getUserByID(userId);
        CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
        Post createdPost = postService.createPost(loggedInUser, createPostRequest);
        PostDto createdPostDto = postMapper.toDto(createdPost);
        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable UUID id,
                                              @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto){
        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
        Post updatePost = postService.updatePost(id, updatePostRequest);
        PostDto updatedPostDto = postMapper.toDto(updatePost);
        return ResponseEntity.ok(updatedPostDto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable UUID id){
        Post post = postService.getPost(id);
        PostDto postDto = postMapper.toDto(post);
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id){
    postService.deletePost(id);
    return ResponseEntity.noContent().build();
    }

}