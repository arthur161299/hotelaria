package com.example.hotelaria.controller;

import com.example.hotelaria.dto.QuartoDTO;
import com.example.hotelaria.model.Quarto;
import com.example.hotelaria.service.QuartoService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/quartos")
public class QuartoController {

    private final QuartoService service;

    public QuartoController(QuartoService service) {
        this.service = service;
    }

    private QuartoDTO converter(Quarto q) {
        return new QuartoDTO(
            q.getId(),
            q.getNumero(),
            q.getTipo(),
            q.getValorDiaria(),
            q.getStatus()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuartoDTO cadastrar(@RequestBody QuartoDTO dto) {

        Quarto quarto = new Quarto();

        quarto.setNumero(dto.numero());
        quarto.setTipo(dto.tipo());
        quarto.setValorDiaria(dto.valorDiaria());
        quarto.setStatus(dto.status());

        return converter(service.cadastrar(quarto));
    }

    @GetMapping
    public List<QuartoDTO> listar() {
        return service.listar()
                .stream()
                .map(this::converter)
                .toList();
    }

    @GetMapping("/{id}")
    public QuartoDTO buscar(@PathVariable Long id) {
        return converter(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public QuartoDTO atualizar(
            @PathVariable Long id,
            @RequestBody QuartoDTO dto) {

        Quarto dados = new Quarto();

        dados.setNumero(dto.numero());
        dados.setTipo(dto.tipo());
        dados.setValorDiaria(dto.valorDiaria());
        dados.setStatus(dto.status());

        return converter(service.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}