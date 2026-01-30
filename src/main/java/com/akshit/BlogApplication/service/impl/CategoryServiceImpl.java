package com.akshit.BlogApplication.service.impl;

import com.akshit.BlogApplication.domain.entity.Category;
import com.akshit.BlogApplication.repository.CategoryRepository;
import com.akshit.BlogApplication.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount();
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        String categoryName = category.getName();
        if (categoryRepository.existsByNameIgnoreCase(categoryName)){
            throw new IllegalArgumentException("Category alredy exists with name: "+ categoryName);
        }

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(UUID id) {
       Optional<Category> category = categoryRepository.findById(id);
       if(category.isPresent()){
           if(!category.get().getPosts().isEmpty()){
               throw new IllegalArgumentException("category have posts");
           }
           categoryRepository.deleteById(id);
       }
    }

    @Override
    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category dose not exists with id : " + id));
    }


    }


