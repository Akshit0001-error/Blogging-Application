package com.akshit.BlogApplication.service;

import com.akshit.BlogApplication.domain.CreatePostRequest;
import com.akshit.BlogApplication.domain.UpdatePostRequest;
import com.akshit.BlogApplication.domain.entity.Post;
import com.akshit.BlogApplication.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface PostService {

    Post getPost(UUID id);
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getDraftPosts(User user);
    Post createPost(User user, CreatePostRequest createPostRequest);
    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
    void deletePost(UUID id);
}
