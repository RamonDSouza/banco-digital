package com.example.registrationlogindemo.service.impl;


import com.example.registrationlogindemo.dto.ContaDTO;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Conta;
import com.example.registrationlogindemo.entity.Transacao;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.ContaRepository;
import com.example.registrationlogindemo.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaServiceImpl {
    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Transactional
    public ContaDTO criarConta(String titular, BigDecimal saldoInicial) {
        Conta conta = new Conta();
        conta.setTitular(titular);
        conta.setSaldo(saldoInicial);
        contaRepository.save(conta);

        return new ContaDTO(conta.getId(), conta.getTitular(), conta.getSaldo());
    }

    @Transactional
    public void creditar(Long contaId, BigDecimal valor) {
        Conta conta = contaRepository.findById(contaId).orElseThrow();
        conta.setSaldo(conta.getSaldo().add(valor));
        contaRepository.save(conta);

        registrarTransacao(contaId, null, valor, "CREDITO");
    }

    @Transactional
    public void debitar(Long contaId, BigDecimal valor) {
        Conta conta = contaRepository.findById(contaId).orElseThrow();
        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente!");
        }
        conta.setSaldo(conta.getSaldo().subtract(valor));
        contaRepository.save(conta);

        registrarTransacao(contaId, null, valor, "DEBITO");
    }

    @Transactional
    public void transferir(Long origemId, Long destinoId, BigDecimal valor) {
        Conta origem = contaRepository.findById(origemId).orElseThrow();
        Conta destino = contaRepository.findById(destinoId).orElseThrow();

        if (origem.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente!");
        }

        origem.setSaldo(origem.getSaldo().subtract(valor));
        destino.setSaldo(destino.getSaldo().add(valor));
        contaRepository.save(origem);
        contaRepository.save(destino);

        registrarTransacao(origemId, destinoId, valor, "TRANSFERENCIA");
    }

    private void registrarTransacao(Long origemId, Long destinoId, BigDecimal valor, String tipo) {
        Transacao transacao = new Transacao();
        transacao.setContaOrigem(origemId);
        transacao.setContaDestino(destinoId);
        transacao.setValor(valor);
        transacao.setData(LocalDateTime.now());
        transacao.setTipo(tipo);
        transacaoRepository.save(transacao);
    }

    @GetMapping("/contas")
    public String listarContas(Model model) {
        List<Conta> contas = contaRepository.findAll();
        model.addAttribute("contas", contas);
        return "contas";
    }
}