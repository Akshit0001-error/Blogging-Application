package com.akshit.BlogApplication.service.impl;

import com.akshit.BlogApplication.domain.CreatePostRequest;
import com.akshit.BlogApplication.domain.PostStatus;
import com.akshit.BlogApplication.domain.UpdatePostRequest;
import com.akshit.BlogApplication.domain.entity.Category;
import com.akshit.BlogApplication.domain.entity.Post;
import com.akshit.BlogApplication.domain.entity.Tag;
import com.akshit.BlogApplication.domain.entity.User;
import com.akshit.BlogApplication.repository.PostRepository;
import com.akshit.BlogApplication.service.CategoryService;
import com.akshit.BlogApplication.service.PostService;
import com.akshit.BlogApplication.service.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;  //p
    private final TagService tagService;  //t
    private final CategoryService categoryService;

    //Assuming a person reads 200 words per minute.
    private static final int wordsPerMin = 200;

    @Override
    public Post getPost(UUID id) {
       return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post with id " + id + " not found :(" ));
    }

    @Override
    @Transactional (readOnly = true)
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
        if( categoryId != null && tagId != null){
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByCategoryAndStatusAndTagsContaining(
                    category,
                    PostStatus.PUBLISHED,
                    tag
            );
        }
        if( categoryId != null){
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByCategoryAndStatus(
                    category,
                    PostStatus.PUBLISHED
            );
        }
        if( tagId != null){
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag
            );
        }
        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public List<Post> getDraftPosts(User user) {
       return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
    }

    @Override
    @Transactional
    public Post createPost(User user, CreatePostRequest createPostRequest) {
        Post newPost = new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setContent(createPostRequest.getContent());
        newPost.setStatus(createPostRequest.getStatus());
        newPost.setAuthor(user);
        newPost.setReadingTime(calculateReadingTime(createPostRequest.getContent()));

        Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());
        newPost.setCategory(category);

        Set<UUID> tagId = createPostRequest.getTagId();
        List<Tag> tags = tagService.getTagByIds(tagId);
        newPost.setTags(new HashSet<>(tags));

        return postRepository.save(newPost);
    }

    @Override
    @Transactional
    public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post dose not exists with id: " + id));

        existingPost.setTitle(updatePostRequest.getTitle());
        String postRequestContent = updatePostRequest.getContent();
        existingPost.setContent(postRequestContent);
        existingPost.setStatus(updatePostRequest.getStatus());
        existingPost.setReadingTime(calculateReadingTime(postRequestContent));

        UUID updatePostRequestCategoryId = updatePostRequest.getCategoryId();
        if( !existingPost.getCategory().getId().equals(updatePostRequestCategoryId)){
            Category newCategory = categoryService.getCategoryById(updatePostRequestCategoryId);
        }
        Set<UUID> getTagsIds = existingPost.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        Set<UUID> tagsId = updatePostRequest.getTagsId();
        if(!getTagsIds.equals(tagsId)){
            List<Tag> newTags = tagService.getTagByIds(tagsId);
            existingPost.setTags(new HashSet<>(newTags));
        }
        return postRepository.save(existingPost);

    }

    @Override
    public void deletePost(UUID id) {
        Post post = getPost(id);
        postRepository.delete(post);
    }

    private Integer calculateReadingTime(String content){
        if(content == null || content.isEmpty()){
            return 0;
        }
        int wordCount = content.trim().split("\\s+").length;
        return (int)Math.ceil((double)wordCount / wordsPerMin);
    }

}
