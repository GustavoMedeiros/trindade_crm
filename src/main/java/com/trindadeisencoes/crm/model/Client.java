package com.trindadeisencoes.crm.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;


@Entity
@Table(name="clients")
public class Client {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message="Nome é obrigatório")
  @Size(max=100, message="Nome não pode ter mais de 100 caracteres")
  private String name;

  @NotBlank(message="CPF é obrigatório")
  @Pattern(regexp="\\d{11}", message="CPF deve ter 11 dígitos numéricos")
  @Column(unique=true)
  private String cpf;

  @NotBlank(message="RG é obrigatório")
  private String rg;

  @NotNull(message="Data de nascimento é obrigatória")
  @Past(message="Nascimento deve ser data passada")
  private LocalDate nascimento;

  @NotBlank(message="Nacionalidade é obrigatória")
  private String nacionalidade;

  @NotBlank(message="Estado civil é obrigatório")
  private String estadoCivil;

  @NotBlank(message="Profissão é obrigatória")
  private String profissao;

  @NotBlank(message="Telefone é obrigatório")
  @Pattern(regexp="\\+?[0-9\\- ]{8,15}", message="Telefone inválido")
  private String telefone;

  @NotBlank(message="WhatsApp é obrigatório")
  @Pattern(regexp="\\+?[0-9\\- ]{8,15}", message="WhatsApp inválido")
  private String whatsapp;

  @NotBlank(message="E-mail é obrigatório")
  @Email(message="Email inválido")
  private String email;

  @NotBlank(message="Endereço é obrigatório")
  private String endereco;

  @NotNull(message="Flag condutor deve ser informada")
  private Boolean condutor;

  @NotBlank(message="Status é obrigatório")
  private String status;

  @ManyToOne @NotNull(message="Vendedor deve ser informado")
  @JoinColumn(name="vendedor_id")
  private User vendedor;

  @Column(name="criado_em", updatable=false)
  private LocalDateTime criadoEm = LocalDateTime.now();

    // ============================
    // Getters e Setters
    // ============================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Boolean getCondutor() {
        return condutor;
    }

    public void setCondutor(Boolean condutor) {
        this.condutor = condutor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getVendedor() {
        return vendedor;
    }

    public void setVendedor(User vendedor) {
        this.vendedor = vendedor;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}