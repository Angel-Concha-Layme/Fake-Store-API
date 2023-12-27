package com.fakestore.api.web.controller;


import com.fakestore.api.dto.ProductCreationDTO;
import com.fakestore.api.dto.ProductResponseDTO;
import com.fakestore.api.persistence.entity.Product;
import com.fakestore.api.service.ProductService;
import com.fakestore.api.util.DateUtil;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @PageableDefault(page =0, size =5) Pageable pageable,
            @RequestParam(required = false) @Size(max = 255) String name,
            @RequestParam(required = false) @Min(0) Double priceMin,
            @RequestParam(required = false) @Min(0) Double priceMax,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) @Min(0) Integer stockQuantityMin,
            @RequestParam(required = false) @Min(0) Integer stockQuantityMax,
            @RequestParam(required = false) String createdAtMin,
            @RequestParam(required = false) String createdAtMax
            ){

        if (priceMin != null && priceMax != null) {
            if (priceMin > priceMax) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        if (stockQuantityMin != null && stockQuantityMax != null) {
            if (stockQuantityMin > stockQuantityMax) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        LocalDateTime minDate = null, maxDate = null;

        if (createdAtMin != null && !createdAtMin.isEmpty()) {
            try {
                minDate = DateUtil.parseDateTime(createdAtMin);
            } catch (DateTimeException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        if (createdAtMax != null && !createdAtMax.isEmpty()) {
            try {
                maxDate = DateUtil.parseDateTime(createdAtMax);
            } catch (DateTimeException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        if (minDate != null && maxDate != null && !DateUtil.isValidDataRange(minDate, maxDate)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Page<ProductResponseDTO> productList = productService.getAllProducts(pageable, name, priceMin, priceMax, categoryId, stockQuantityMin, stockQuantityMax, minDate, maxDate);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id){
        Product product =  productService.getProductById(id);

        ProductResponseDTO productResponseDto = new ProductResponseDTO(
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

        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductCreationDTO productCreationDTO){
        Product createdProduct = productService.createProduct(productCreationDTO);

        ProductResponseDTO productResponseDto = new ProductResponseDTO(
                createdProduct.getId(),
                createdProduct.getName(),
                createdProduct.getDescription(),
                createdProduct.getPrice(),
                createdProduct.getStockQuantity(),
                createdProduct.getCategory().getName(),
                createdProduct.getImageUrl(),
                createdProduct.getCreatedAt(),
                createdProduct.getUpdatedAt()
        );

        return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductCreationDTO productCreationDTO){
        Product updatedProduct = productService.updateProduct(id, productCreationDTO);

        ProductResponseDTO productResponseDto = new ProductResponseDTO(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice(),
                updatedProduct.getStockQuantity(),
                updatedProduct.getCategory().getName(),
                updatedProduct.getImageUrl(),
                updatedProduct.getCreatedAt(),
                updatedProduct.getUpdatedAt()
        );

        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

