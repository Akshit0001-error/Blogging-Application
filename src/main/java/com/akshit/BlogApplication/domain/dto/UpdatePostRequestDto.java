package com.akshit.BlogApplication.domain.dto;

import com.akshit.BlogApplication.domain.PostStatus;
import jakarta.persistence.SecondaryTable;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePostRequestDto {

    @NotNull(message = "Post id is required")
    private UUID id;

    @NotBlank(message = "Title is required")
    @Size(min = 2,max = 100, message = "Title must be in between {min} and {max} characters")
    private String title;

    @NotBlank(message = "Some content is required")
    @Size(min = 10,max = 10000, message = "Content must be in between {min} and {max} characters")
    private String content;

    @NotNull(message = "Category id is required")
    private UUID categoryId;

    @Builder.Default
    @Size(max =10,message = "Maximum {max} tags are allowed")
    private Set<UUID> tagsId = new HashSet<>();

    @NotNull(message = "Status is required")
    private PostStatus status;

}
