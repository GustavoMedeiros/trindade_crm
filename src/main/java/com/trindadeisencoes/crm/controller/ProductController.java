package com.trindadeisencoes.crm.controller;

import com.trindadeisencoes.crm.model.Product;
import com.trindadeisencoes.crm.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.Optional;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/produtos")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // POST - Criar produto (VENDEDOR)
    @PostMapping
    public ResponseEntity<?> criarProduto(@RequestBody @Valid Product produto) {
        return ResponseEntity.ok(productRepository.save(produto));
    }

    // GET - Listar todos os produtos (VENDEDOR e GESTOR)
    @GetMapping
    public ResponseEntity<List<Product>> listarProdutos() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    // PUT - Atualizar produto (VENDEDOR)
    @PutMapping("/{id}")
    public ResponseEntity<Product> atualizarProduto(@PathVariable Long id, @RequestBody Product novoProduto) {
        Optional<Product> existente = productRepository.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product produto = existente.get();
        produto.setNome(novoProduto.getNome());
        produto.setPrecoPadrao(novoProduto.getPrecoPadrao());
        produto.setStatus(novoProduto.getStatus());

        return ResponseEntity.ok(productRepository.save(produto));
    }

    // DELETE - Remover produto (GESTOR)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}