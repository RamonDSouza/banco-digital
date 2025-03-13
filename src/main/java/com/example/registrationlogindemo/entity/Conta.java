package com.example.registrationlogindemo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name="conta")
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O titular é obrigatório.")
    private String titular;
    @NotNull(message = "O saldo inicial é obrigatório.")
    @Min(value = 0, message = "O saldo inicial não pode ser negativo.")
    private BigDecimal saldo;

    @Version
    private Integer versao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Integer getVersao() {
        return versao;
    }

    public void setVersao(Integer versao) {
        this.versao = versao;
    }

    public Conta(String titular, Long id, Integer versao, BigDecimal saldo) {
        this.titular = titular;
        this.id = id;
        this.versao = versao;
        this.saldo = saldo;
    }

    public Conta() {

    }
}
