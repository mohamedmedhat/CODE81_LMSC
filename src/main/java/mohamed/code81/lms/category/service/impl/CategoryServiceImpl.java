/*
 * Copyright (c) 2025 Mohamed Medhat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy at: http://www.apache.org/licenses/LICENSE-2.0
 */

package mohamed.code81.lms.category.service.impl;

import lombok.RequiredArgsConstructor;
import mohamed.code81.lms.category.Category;
import mohamed.code81.lms.category.CategoryMapper;
import mohamed.code81.lms.category.CategoryRepository;
import mohamed.code81.lms.category.dto.request.CategoryRequestDto;
import mohamed.code81.lms.category.dto.response.CategoryResponseDto;
import mohamed.code81.lms.category.exception.CategoryNotFoundException;
import mohamed.code81.lms.category.service.CategoryExplorerService;
import mohamed.code81.lms.category.service.CategoryService;
import mohamed.code81.lms.common.response.PageableResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService, CategoryExplorerService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category getById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("category with id: "+id+" not found"));
    }

    @Override
    public List<Category> getByNames(List<String> names) {
        return names.stream()
                .map(d -> categoryRepository.findByName(d)
                        .orElseThrow(() -> new CategoryNotFoundException("Category with name: " + d+ " not found")))
                .toList();
    }

    @Override
    public List<CategoryResponseDto> createCategories(List<CategoryRequestDto> dto) {

        List<Category> categories = dto.stream()
                .map(categoryMapper::toCategory)
                .toList();

        categories = categoryRepository.saveAll(categories);

        return categories.stream()
                .map(categoryMapper::categoryResponseDto)
                .toList();
    }

    @Override
    public void deleteCategories(List<UUID> ids) {
        categoryRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public PageableResponseDto<CategoryResponseDto> getCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Category> categories = categoryRepository.findAll(pageable);

        return categoryMapper.toPageableCategoryResponse(categories);
    }
}
