package com.fakestore.api.service;

import com.fakestore.api.dto.ProductCreationDTO;
import com.fakestore.api.dto.ProductResponseDTO;
import com.fakestore.api.exception.ProductAlreadyExistsException;
import com.fakestore.api.exception.ProductNotFoundException;
import com.fakestore.api.persistence.entity.Category;
import com.fakestore.api.persistence.entity.Product;
import com.fakestore.api.persistence.repository.CategoryRepository;
import com.fakestore.api.persistence.repository.ProductRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;


    public Page<ProductResponseDTO> getAllProducts(Pageable pageable, String name, Double priceMin, Double priceMax, Long categoryId, Integer stockQuantityMin, Integer stockQuantityMax, LocalDateTime createdAtMin, LocalDateTime createdAtMax) {
        Specification<Product> spec = buildSpecification(name, priceMin, priceMax, categoryId, stockQuantityMin, stockQuantityMax, createdAtMin, createdAtMax);
        Page<Product> productList = productRepository.findAll(spec, pageable);
        return productList.map(this::convertToDto);

        //Page<Product> productList = productRepository.findAll(pageable);
        //return productList.map(this::convertToDto);
    }

    private Specification<Product> buildSpecification(String name, Double priceMin, Double priceMax, Long categoryId, Integer stockQuantityMin, Integer stockQuantityMax, LocalDateTime createdAtMin, LocalDateTime createdAtMax) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (priceMin != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), priceMin));
            }
            if (priceMax != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), priceMax));
            }
            if (categoryId != null) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }
            if (stockQuantityMin != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("stockQuantity"), stockQuantityMin));
            }
            if (stockQuantityMax != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("stockQuantity"), stockQuantityMax));
            }
            if (createdAtMin != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), createdAtMin));
            }
            if (createdAtMax != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), createdAtMax));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
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

    public boolean existsById(Long aLong) {
        return productRepository.existsById(aLong);
    }
}
