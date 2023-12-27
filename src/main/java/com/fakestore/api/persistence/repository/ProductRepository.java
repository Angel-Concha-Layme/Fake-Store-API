package com.fakestore.api.persistence.repository;

import com.fakestore.api.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Object> findByName(String name);

    boolean existsByName(String name);

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
}
