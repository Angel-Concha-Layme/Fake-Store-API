package com.fakestore.api.web.controller;

import com.fakestore.api.dto.CategoryCreationDTO;
import com.fakestore.api.dto.CategoryResponseDTO;
import com.fakestore.api.dto.ProductResponseDTO;
import com.fakestore.api.persistence.entity.Category;
import com.fakestore.api.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryCreationDTO categoryCreationDTO){
        Category createdCategory = categoryService.createCategory(categoryCreationDTO);

        CategoryResponseDTO categoryResponseDto = new CategoryResponseDTO(
                createdCategory.getId(),
                createdCategory.getName(),
                createdCategory.getDescription()
        );

        return new ResponseEntity<>(categoryResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        List<CategoryResponseDTO> categoryList = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id){
        Category category =  categoryService.getCategoryById(id);

        CategoryResponseDTO categoryResponseDto = new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );

        return new ResponseEntity<>(categoryResponseDto, HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryCreationDTO categoryCreationDTO){
        Category updatedCategory = categoryService.updateCategory(id, categoryCreationDTO);

        CategoryResponseDTO categoryResponseDto = new CategoryResponseDTO(
                updatedCategory.getId(),
                updatedCategory.getName(),
                updatedCategory.getDescription()
        );

        return new ResponseEntity<>(categoryResponseDto, HttpStatus.OK);
    }
}
