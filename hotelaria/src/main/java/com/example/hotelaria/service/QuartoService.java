package com.example.hotelaria.service;

import com.example.hotelaria.model.Quarto;
import com.example.hotelaria.repository.QuartoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuartoService {

    private final QuartoRepository repository;

    public QuartoService(QuartoRepository repository) {
        this.repository = repository;
    }

    public Quarto cadastrar(Quarto quarto) {
        return repository.save(quarto);
    }

    public List<Quarto> listar() {
        return repository.findAll();
    }

    public Quarto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Quarto não encontrado"));
    }

    public Quarto atualizar(Long id, Quarto dados) {

        Quarto quarto = buscarPorId(id);

        quarto.setNumero(dados.getNumero());
        quarto.setTipo(dados.getTipo());
        quarto.setValorDiaria(dados.getValorDiaria());
        quarto.setStatus(dados.getStatus());

        return repository.save(quarto);
    }

    @Transactional
    public void excluir(Long id) {

        Quarto quarto = buscarPorId(id);

        if (!quarto.getReservas().isEmpty()) {
            throw new IllegalStateException(
                "Não é possível excluir quarto associado a reservas"
            );
        }

        repository.delete(quarto);
    }
}