package com.akshit.BlogApplication.mapper;

import com.akshit.BlogApplication.domain.CreatePostRequest;
import com.akshit.BlogApplication.domain.UpdatePostRequest;
import com.akshit.BlogApplication.domain.dto.CreatePostRequestDto;
import com.akshit.BlogApplication.domain.dto.PostDto;
import com.akshit.BlogApplication.domain.dto.UpdatePostRequestDto;
import com.akshit.BlogApplication.domain.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {


   // @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "status", source = "status")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);

    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto dto);

}
