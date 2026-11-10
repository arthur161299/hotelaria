package com.example.hotelaria.service;

import com.example.hotelaria.model.Servico;
import com.example.hotelaria.repository.ServicoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository repository;

    public ServicoService(ServicoRepository repository) {
        this.repository = repository;
    }

    public Servico cadastrar(Servico servico) {
        return repository.save(servico);
    }

    public List<Servico> listar() {
        return repository.findAll();
    }

    public Servico buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Serviço não encontrado"));
    }

    public Servico atualizar(Long id, Servico dados) {

        Servico servico = buscarPorId(id);

        servico.setNome(dados.getNome());
        servico.setDescricao(dados.getDescricao());
        servico.setPreco(dados.getPreco());

        return repository.save(servico);
    }

    @Transactional
    public void excluir(Long id) {

        Servico servico = buscarPorId(id);

        if (!servico.getReservas().isEmpty()) {
            throw new IllegalStateException(
                "Não é possível excluir serviço associado a reservas"
            );
        }

        repository.delete(servico);
    }
}