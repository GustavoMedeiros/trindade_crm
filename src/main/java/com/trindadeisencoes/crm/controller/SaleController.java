package com.trindadeisencoes.crm.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.trindadeisencoes.crm.model.Client;
import com.trindadeisencoes.crm.model.Product;
import com.trindadeisencoes.crm.model.Sale;
import com.trindadeisencoes.crm.model.User;
import com.trindadeisencoes.crm.repository.ClientRepository;
import com.trindadeisencoes.crm.repository.ProductRepository;
import com.trindadeisencoes.crm.repository.SaleRepository;
import com.trindadeisencoes.crm.repository.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/vendas")
public class SaleController {

    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public SaleController(SaleRepository saleRepository,
                          ClientRepository clientRepository,
                          ProductRepository productRepository,
                          UserRepository userRepository) {
        this.saleRepository = saleRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> criarVenda(@RequestBody Sale sale) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User vendedor = userRepository.findByEmail(email).orElse(null);

        if (vendedor == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado.");

        if (sale.getClient() == null || sale.getClient().getId() == null)
            return ResponseEntity.badRequest().body("Cliente não informado.");

        Client cliente = clientRepository.findById(sale.getClient().getId()).orElse(null);
        if (cliente == null || cliente.getVendedor() == null || !cliente.getVendedor().getId().equals(vendedor.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não pode cadastrar vendas para este cliente.");

        if (sale.getProduto() == null || sale.getProduto().getId() == null)
            return ResponseEntity.badRequest().body("Produto não informado.");

        Product produto = productRepository.findById(sale.getProduto().getId()).orElse(null);
        if (produto == null) return ResponseEntity.badRequest().body("Produto inválido.");

        sale.setClient(cliente);
        sale.setProduto(produto);
        sale.setVendedor(vendedor);
        sale.setDataVenda(LocalDateTime.now());

        Sale salva = saleRepository.save(sale);
        return ResponseEntity.ok(salva);
    }

    @GetMapping
    public ResponseEntity<?> listarTodasVendas() {
        List<Sale> vendas = saleRepository.findAll();
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> listarPorCliente(@PathVariable Long id) {
        List<Sale> vendas = saleRepository.findByClientId(id);
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/produto/{id}")
    public ResponseEntity<?> listarPorProduto(@PathVariable Long id){
        List<Sale> vendas = saleRepository.findByProdutoId(id);
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/vendedor/{id}")
    public ResponseEntity<?> listarPorVendedor(@PathVariable Long id){
        List<Sale> vendas = saleRepository.findByVendedorId(id);
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<?> filtrarPorData(
        @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
        @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim){
        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime fimDateTime = fim.atTime(23, 59, 59);
        List<Sale> vendas = saleRepository.findByDataVendaBetween(inicioDateTime, fimDateTime);
        return ResponseEntity.ok(vendas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarVenda(@PathVariable Long id, @RequestBody Sale atualizacao) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User vendedor = userRepository.findByEmail(email).orElse(null);
        if (vendedor == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado.");

        Sale existente = saleRepository.findById(id).orElse(null);
        if (existente == null) return ResponseEntity.notFound().build();

        if (!existente.getVendedor().getId().equals(vendedor.getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Apenas o vendedor criador pode atualizar a venda.");

        if (atualizacao.getProduto() == null || atualizacao.getProduto().getId() == null)
            return ResponseEntity.badRequest().body("Produto não informado.");

        Product produto = productRepository.findById(atualizacao.getProduto().getId()).orElse(null);
        if (produto == null) return ResponseEntity.badRequest().body("Produto inválido.");

        existente.setProduto(produto);
        existente.setValor(atualizacao.getValor());

        Sale atualizada = saleRepository.save(existente);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarVenda(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isGestor = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("GESTOR"));
        if (!isGestor) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Apenas gestores podem excluir vendas.");

        if (!saleRepository.existsById(id)) return ResponseEntity.notFound().build();

        saleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}