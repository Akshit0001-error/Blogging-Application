package com.akshit.BlogApplication.controller;

import com.akshit.BlogApplication.domain.dto.CreateTagRequest;
import com.akshit.BlogApplication.domain.dto.TagDto;
import com.akshit.BlogApplication.domain.entity.Tag;
import com.akshit.BlogApplication.mapper.TagMapper;
import com.akshit.BlogApplication.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags(){
      List<Tag> tags  = tagService.getTags();
      List<TagDto> tagResponses = tags.stream().map(tag -> tagMapper.toTagResponse(tag)).toList();
      return ResponseEntity.ok(tagResponses);
    }

    @PostMapping
    public ResponseEntity<List<TagDto>> createTags(@RequestBody CreateTagRequest createTagRequest){
        List<Tag> savedTags = tagService.createTags(createTagRequest.getNames());
        List<TagDto> createdTagResponses = savedTags.stream()
                .map(tagMapper::toTagResponse)
                .toList();

        return new ResponseEntity<>(createdTagResponses,HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id){
     tagService.deleteTag(id);
     return ResponseEntity.noContent().build();
    }

}
