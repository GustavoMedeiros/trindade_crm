package com.trindadeisencoes.crm.service;

import com.trindadeisencoes.crm.model.Product;
import com.trindadeisencoes.crm.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> listarTodos(){
        return productRepository.findAll();
    }

    public Optional<Product> buscarPorId(Long id){
        return productRepository.findById(id);
    }

    public Product salvar(Product product){
        return productRepository.save(product);
    }

    public void deletar(Long id){
        productRepository.deleteById(id);
    }
}