package com.fakestore.api.service;

import com.fakestore.api.dto.ProductCreationDTO;
import com.fakestore.api.dto.ProductResponseDTO;
import com.fakestore.api.exception.ProductAlreadyExistsException;
import com.fakestore.api.exception.ProductNotFoundException;
import com.fakestore.api.persistence.entity.Category;
import com.fakestore.api.persistence.entity.Product;
import com.fakestore.api.persistence.repository.CategoryRepository;
import com.fakestore.api.persistence.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;


    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with ID: " + id)
        );
    }

    public Product createProduct(ProductCreationDTO productCreationDTO) {
        validateProductDoesNotExistForName(productCreationDTO.name());

        Product product = new Product();
        product.setName(productCreationDTO.name());
        product.setDescription(productCreationDTO.description());
        product.setPrice(productCreationDTO.price());
        product.setStockQuantity(productCreationDTO.stockQuantity());
        Category category = categoryRepository.findById(productCreationDTO.categoryId())
                .orElseThrow();
        product.setCategory(category);
        product.setImageUrl(productCreationDTO.imageUrl());
        product.setCreatedAt(LocalDate.now());
        product.setUpdatedAt(LocalDate.now());

        productRepository.save(product);
        return product;
    }
    public void validateProductDoesNotExistForName(String name){
        productRepository.findByName(name)
                .ifPresent(product -> {
                    throw new ProductAlreadyExistsException("Product already exists with name: " + name);
                });
    }

    public ProductResponseDTO convertToDto(Product product){
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory().getName(),
                product.getImageUrl(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    public Product updateProduct(Long id, ProductCreationDTO productCreationDTO) {
        Product product = getProductById(id);
        product.setName(productCreationDTO.name());
        product.setDescription(productCreationDTO.description());
        product.setPrice(productCreationDTO.price());
        product.setStockQuantity(productCreationDTO.stockQuantity());
        Category category = categoryRepository.findById(productCreationDTO.categoryId())
                .orElseThrow();
        product.setCategory(category);
        product.setImageUrl(productCreationDTO.imageUrl());
        product.setUpdatedAt(LocalDate.now());
        productRepository.save(product);
        return product;
    }
}
