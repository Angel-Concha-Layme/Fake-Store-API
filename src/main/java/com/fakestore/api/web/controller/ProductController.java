package com.fakestore.api.web.controller;


import com.fakestore.api.dto.ProductCreationDTO;
import com.fakestore.api.dto.ProductResponseDTO;
import com.fakestore.api.persistence.entity.Product;
import com.fakestore.api.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        List<ProductResponseDTO> productList = productService.getAllProducts();
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

