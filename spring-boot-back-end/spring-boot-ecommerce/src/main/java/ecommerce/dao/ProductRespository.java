package com.jiahaieconproj.ecommerce.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.jiahaieconproj.ecommerce.entity.Product;

/**
 * ProductRespository
 */
@CrossOrigin("http://localhost:4200")
public interface ProductRespository extends JpaRepository<Product, Long>//<entityType, primaryKeyType>
{
    Page<Product> findByCategoryId(@Param("id") Long id, Pageable pageable);
    
    Page<Product> findByNameContaining(@Param("name") String name, Pageable page);
}