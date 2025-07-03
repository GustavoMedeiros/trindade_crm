package com.trindadeisencoes.crm.repository;

import com.trindadeisencoes.crm.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}