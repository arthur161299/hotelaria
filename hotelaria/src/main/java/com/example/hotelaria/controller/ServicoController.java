package com.example.hotelaria.controller;

import com.example.hotelaria.dto.ServicoDTO;
import com.example.hotelaria.model.Servico;
import com.example.hotelaria.service.ServicoService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService service;

    public ServicoController(ServicoService service) {
        this.service = service;
    }

    private ServicoDTO converter(Servico s) {
        return new ServicoDTO(
            s.getId(),
            s.getNome(),
            s.getDescricao(),
            s.getPreco()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoDTO cadastrar(@RequestBody ServicoDTO dto) {

        Servico servico = new Servico();

        servico.setNome(dto.nome());
        servico.setDescricao(dto.descricao());
        servico.setPreco(dto.preco());

        return converter(service.cadastrar(servico));
    }

    @GetMapping
    public List<ServicoDTO> listar() {
        return service.listar()
                .stream()
                .map(this::converter)
                .toList();
    }

    @GetMapping("/{id}")
    public ServicoDTO buscar(@PathVariable Long id) {
        return converter(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ServicoDTO atualizar(
            @PathVariable Long id,
            @RequestBody ServicoDTO dto) {

        Servico dados = new Servico();

        dados.setNome(dto.nome());
        dados.setDescricao(dto.descricao());
        dados.setPreco(dto.preco());

        return converter(service.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}