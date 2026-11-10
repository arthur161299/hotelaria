package com.example.hotelaria.controller;

import com.example.hotelaria.dto.*;
import com.example.hotelaria.model.*;
import com.example.hotelaria.service.ReservaService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    private ReservaResponseDTO converter(Reserva r) {
        return new ReservaResponseDTO(
            r.getId(),
            r.getHospede().getId(),
            r.getQuartos().stream()
                .map(Quarto::getId).toList(),
            r.getServicos().stream()
                .map(Servico::getId).toList(),
            r.getDataEntrada(),
            r.getDataSaida(),
            r.getStatus(),
            r.getValorTotal()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaResponseDTO cadastrar(
            @Valid @RequestBody ReservaRequestDTO dto) {

        Reserva reserva = service.cadastrar(
            dto.hospedeId(),
            dto.quartosIds(),
            dto.servicosIds(),
            dto.dataEntrada(),
            dto.dataSaida()
        );

        return converter(reserva);
    }

    @GetMapping
    public List<ReservaResponseDTO> listar() {
        return service.listar()
            .stream()
            .map(this::converter)
            .toList();
    }

    @GetMapping("/{id}")
    public ReservaResponseDTO buscar(@PathVariable Long id) {
        return converter(service.buscarPorId(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ReservaResponseDTO cancelar(@PathVariable Long id) {
        return converter(service.cancelar(id));
    }
}