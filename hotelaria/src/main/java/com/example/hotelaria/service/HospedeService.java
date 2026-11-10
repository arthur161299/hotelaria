package com.example.hotelaria.service;

import com.example.hotelaria.model.Hospede;
import com.example.hotelaria.repository.HospedeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HospedeService {

    private final HospedeRepository repository;

    public HospedeService(HospedeRepository repository) {
        this.repository = repository;
    }

    // Cadastrar hóspede
    public Hospede cadastrar(Hospede hospede) {
        return repository.save(hospede);
    }

    // Listar hóspedes
    public List<Hospede> listar() {
        return repository.findAll();
    }

    // Buscar hóspede pelo ID
    public Hospede buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Hóspede não encontrado"));
    }

    // Atualizar hóspede
    public Hospede atualizar(Long id, Hospede dados) {

        Hospede hospede = buscarPorId(id);

        hospede.setNome(dados.getNome());
        hospede.setCpf(dados.getCpf());
        hospede.setEmail(dados.getEmail());
        hospede.setTelefone(dados.getTelefone());

        return repository.save(hospede);
    }

    // Excluir hóspede
    @Transactional
    public void excluir(Long id) {

        Hospede hospede = buscarPorId(id);

        if (!hospede.getReservas().isEmpty()) {
            throw new IllegalStateException(
                "Não é possível excluir hóspede com reservas"
            );
        }

        repository.delete(hospede);
    }
}