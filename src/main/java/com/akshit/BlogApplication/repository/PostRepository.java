package com.akshit.BlogApplication.repository;

import com.akshit.BlogApplication.domain.PostStatus;
import com.akshit.BlogApplication.domain.entity.Category;
import com.akshit.BlogApplication.domain.entity.Post;
import com.akshit.BlogApplication.domain.entity.Tag;
import com.akshit.BlogApplication.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
  List<Post> findAllByCategoryAndStatusAndTagsContaining(Category category, PostStatus status, Tag tag);
  List<Post> findAllByCategoryAndStatus(Category category, PostStatus status);
  List<Post> findAllByStatusAndTagsContaining(PostStatus status, Tag tag);
  List<Post> findAllByStatus(PostStatus status);
  List<Post> findAllByAuthorAndStatus(User author, PostStatus status);
}
