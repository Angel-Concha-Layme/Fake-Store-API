package com.fakestore.api.service;

import com.fakestore.api.dto.CategoryCreationDTO;
import com.fakestore.api.dto.CategoryResponseDTO;
import com.fakestore.api.exception.CategoryNotFoundException;
import com.fakestore.api.persistence.entity.Category;
import com.fakestore.api.persistence.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;
    public Category createCategory(CategoryCreationDTO categoryCreationDTO) {
        Category category = new Category();
        category.setName(categoryCreationDTO.name());
        category.setDescription(categoryCreationDTO.description());
        categoryRepository.save(category);
        return category;
    }

    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO convertToDto(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public Category updateCategory(Long id, CategoryCreationDTO categoryCreationDTO) {
        Category category = getCategoryById(id);
        category.setName(categoryCreationDTO.name());
        category.setDescription(categoryCreationDTO.description());
        categoryRepository.save(category);
        return category;
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found with ID: " + id)
        );
    }
}
