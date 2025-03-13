package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.ContaDTO;
import com.example.registrationlogindemo.entity.Transacao;
import com.example.registrationlogindemo.service.impl.ContaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaServiceImpl contaService;

    @PostMapping
    public ResponseEntity<ContaDTO> criarConta(@RequestBody ContaDTO dto) {
        return ResponseEntity.ok(contaService.criarConta(dto.getTitular(), dto.getSaldo()));
    }

    @PostMapping("/{id}/creditar")
    public ResponseEntity<Void> creditar(@PathVariable Long id, @RequestBody Map<String, BigDecimal> request) {
        contaService.creditar(id, request.get("valor"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/debitar")
    public ResponseEntity<Void> debitar(@PathVariable Long id, @RequestBody Map<String, BigDecimal> request) {
        contaService.debitar(id, request.get("valor"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transferir")
    public ResponseEntity<Void> transferir(@RequestBody Transacao dto) {
        contaService.transferir(dto.getContaOrigem(), dto.getContaDestino(), dto.getValor());
        return ResponseEntity.ok().build();
    }
}
