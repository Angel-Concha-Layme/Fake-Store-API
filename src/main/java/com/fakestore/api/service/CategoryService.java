package com.fakestore.api.service;

import com.fakestore.api.dto.CategoryCreationDTO;
import com.fakestore.api.dto.CategoryResponseDTO;
import com.fakestore.api.exception.CategoryNotFoundException;
import com.fakestore.api.persistence.entity.Category;
import com.fakestore.api.persistence.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<CategoryResponseDTO> getAllCategories(Pageable pageable) {
        Page<Category> categoryList = categoryRepository.findAll(pageable);
        return categoryList.map(this::convertToDto);
    }

    public CategoryResponseDTO convertToDto(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getImage(),
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
