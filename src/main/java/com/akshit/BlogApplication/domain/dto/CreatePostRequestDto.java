package com.akshit.BlogApplication.domain.dto;

import com.akshit.BlogApplication.domain.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequestDto {

    @NotNull(message = "ID is required")
    private UUID categoryId;

    @Builder.Default
    @Size(max =10,message = "Maximum {max} tags are allowed")
    private Set<UUID> tagsId = new HashSet<>();

    @NotNull(message = "Status is required")
    private PostStatus status;

    @NotBlank(message = "Title is required")
    @Size(min = 2,max = 30,message = "Title must be between {min} and {max}")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 10,max = 100000,message = "Content must be between {min} and {max}")
    private String content;




}
