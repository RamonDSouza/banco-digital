package com.example.registrationlogindemo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ContaDTO {
        private Long id;
        @NotBlank(message = "O titular é obrigatório.")
        private String titular;

        @NotNull(message = "O saldo é obrigatório.")
        @Min(value = 0, message = "O saldo não pode ser negativo.")
        private BigDecimal saldo;

        public ContaDTO(Long id, String titular, BigDecimal saldo) {
        }

        public BigDecimal getSaldo() {
                return saldo;
        }

        public void setSaldo(BigDecimal saldo) {
                this.saldo = saldo;
        }

        public String getTitular() {
                return titular;
        }

        public void setTitular(String titular) {
                this.titular = titular;
        }

        public ContaDTO(String titular, BigDecimal saldo, Long id) {
                this.titular = titular;
                this.saldo = saldo;
                this.id = id;
        }

        public ContaDTO() {
        }
}
