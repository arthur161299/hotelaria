package com.example.hotelaria.controller;

import com.example.hotelaria.dto.*;
import com.example.hotelaria.model.Pagamento;
import com.example.hotelaria.service.PagamentoService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    private PagamentoResponseDTO converter(Pagamento p) {
        return new PagamentoResponseDTO(
            p.getId(),
            p.getReserva().getId(),
            p.getValor(),
            p.getDataPagamento(),
            p.getFormaPagamento(),
            p.getStatus()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PagamentoResponseDTO cadastrar(
            @Valid @RequestBody PagamentoRequestDTO dto) {

        Pagamento pagamento = service.cadastrar(
            dto.reservaId(),
            dto.valor(),
            dto.formaPagamento()
        );

        return converter(pagamento);
    }

    @GetMapping
    public List<PagamentoResponseDTO> listar() {
        return service.listar()
            .stream()
            .map(this::converter)
            .toList();
    }

    @GetMapping("/{id}")
    public PagamentoResponseDTO buscar(@PathVariable Long id) {
        return converter(service.buscarPorId(id));
    }
}