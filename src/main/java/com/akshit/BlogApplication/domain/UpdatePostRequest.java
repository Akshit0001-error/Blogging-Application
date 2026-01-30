package com.akshit.BlogApplication.domain;

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
public class UpdatePostRequest {
    private UUID id;
    private String content;
    private String title;
    private UUID categoryId;

    @Builder.Default
    private Set<UUID> tagsId = new HashSet<>();
    private PostStatus status;
}
