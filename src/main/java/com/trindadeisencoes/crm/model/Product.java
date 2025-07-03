package com.trindadeisencoes.crm.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;

@Entity
@Table(name="product")
public class Product {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Nome do produto é obrigatório")
    private String nome;

    @NotNull(message="Preço é obrigatório")
    @DecimalMin(value="0.0", inclusive=false, message="Preço deve ser maior que zero")
    private BigDecimal precoPadrao;

    private String status; //"ATIVO", "INATIVO"

    // ============================
    // Getters e Setters
    // ============================
    
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public BigDecimal getPrecoPadrao(){
        return precoPadrao;
    }

    public void setPrecoPadrao(BigDecimal precoPadrao){
        this.precoPadrao = precoPadrao;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}