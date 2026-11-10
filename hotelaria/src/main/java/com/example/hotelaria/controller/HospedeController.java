package com.example.hotelaria.controller;

import com.example.hotelaria.dto.HospedeDTO;
import com.example.hotelaria.model.Hospede;
import com.example.hotelaria.service.HospedeService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/hospedes")
public class HospedeController {

    private final HospedeService service;

    public HospedeController(HospedeService service) {
        this.service = service;
    }

    private HospedeDTO converter(Hospede h) {
        return new HospedeDTO(
            h.getId(),
            h.getNome(),
            h.getCpf(),
            h.getEmail(),
            h.getTelefone()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HospedeDTO cadastrar(@RequestBody HospedeDTO dto) {

        Hospede hospede = new Hospede();

        hospede.setNome(dto.nome());
        hospede.setCpf(dto.cpf());
        hospede.setEmail(dto.email());
        hospede.setTelefone(dto.telefone());

        return converter(service.cadastrar(hospede));
    }

    @GetMapping
    public List<HospedeDTO> listar() {
        return service.listar()
                .stream()
                .map(this::converter)
                .toList();
    }

    @GetMapping("/{id}")
    public HospedeDTO buscar(@PathVariable Long id) {
        return converter(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public HospedeDTO atualizar(
            @PathVariable Long id,
            @RequestBody HospedeDTO dto) {

        Hospede dados = new Hospede();

        dados.setNome(dto.nome());
        dados.setCpf(dto.cpf());
        dados.setEmail(dto.email());
        dados.setTelefone(dto.telefone());

        return converter(service.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}