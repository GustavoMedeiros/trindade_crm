package com.trindadeisencoes.crm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Valor da venda é obrigatório.")
    @DecimalMin(value = "0.01", message = "Valor da venda deve ser maior que zero.")
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @NotNull(message = "Cliente da venda é obrigatório.")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    @NotNull(message = "Produto da venda é obrigatório.")
    private Product produto;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    @NotNull(message = "Vendedor da venda é obrigatório.")
    private User vendedor;

    @NotNull(message = "Data da venda é obrigatória.")
    private LocalDateTime dataVenda;

    @NotBlank(message = "Status da venda é obrigatório.")
    @Pattern(regexp = "ATIVA|CANCELADA|EM_ANALISE", message = "Status deve ser: ATIVA, CANCELADA ou EM_ANALISE.")
    private String status;

    // ====================
    // Getters e Setters
    // ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduto() {
        return produto;
    }

    public void setProduto(Product produto) {
        this.produto = produto;
    }

    public User getVendedor() {
        return vendedor;
    }

    public void setVendedor(User vendedor) {
        this.vendedor = vendedor;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ====================
    // Construtores
    // ====================

    public Sale() {
    }

    public Sale(BigDecimal valor, Client client, Product produto, User vendedor, LocalDateTime dataVenda, String status) {
        this.valor = valor;
        this.client = client;
        this.produto = produto;
        this.vendedor = vendedor;
        this.dataVenda = dataVenda;
        this.status = status;
    }
}