package com.example.hotelaria.service;

import com.example.hotelaria.model.*;
import com.example.hotelaria.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final HospedeRepository hospedeRepository;
    private final QuartoRepository quartoRepository;
    private final ServicoRepository servicoRepository;

    public ReservaService(
            ReservaRepository reservaRepository,
            HospedeRepository hospedeRepository,
            QuartoRepository quartoRepository,
            ServicoRepository servicoRepository) {

        this.reservaRepository = reservaRepository;
        this.hospedeRepository = hospedeRepository;
        this.quartoRepository = quartoRepository;
        this.servicoRepository = servicoRepository;
    }

    // CADASTRAR RESERVA
    @Transactional
    public Reserva cadastrar(
            Long hospedeId,
            List<Long> quartosIds,
            List<Long> servicosIds,
            LocalDate entrada,
            LocalDate saida) {

        if (entrada == null || saida == null ||
                !saida.isAfter(entrada)) {
            throw new IllegalArgumentException(
                "A data de saída deve ser posterior à entrada"
            );
        }

        if (hospedeId == null) {
            throw new IllegalArgumentException(
                "Informe o hóspede"
            );
        }

        if (quartosIds == null || quartosIds.isEmpty()) {
            throw new IllegalArgumentException(
                "Informe pelo menos um quarto"
            );
        }

        if (new HashSet<>(quartosIds).size() != quartosIds.size()) {
            throw new IllegalArgumentException(
                "Existem quartos repetidos"
            );
        }

        Hospede hospede = hospedeRepository.findById(hospedeId)
                .orElseThrow(() ->
                    new RuntimeException("Hóspede não encontrado"));

        List<Quarto> quartos = new ArrayList<>();

        for (Long quartoId : quartosIds) {

            Quarto quarto = quartoRepository.findById(quartoId)
                    .orElseThrow(() ->
                        new RuntimeException("Quarto não encontrado"));

            if (!"DISPONIVEL".equalsIgnoreCase(quarto.getStatus())) {
                throw new IllegalStateException(
                    "Quarto indisponível: " + quarto.getNumero()
                );
            }

            if (!reservaRepository.buscarConflitos(
                    quartoId, entrada, saida).isEmpty()) {
                throw new IllegalStateException(
                    "Quarto já reservado: " + quarto.getNumero()
                );
            }

            quartos.add(quarto);
        }

        List<Servico> servicos = new ArrayList<>();

        if (servicosIds != null) {

            if (new HashSet<>(servicosIds).size() != servicosIds.size()) {
                throw new IllegalArgumentException(
                    "Existem serviços repetidos"
                );
            }

            for (Long servicoId : servicosIds) {

                Servico servico = servicoRepository
                        .findById(servicoId)
                        .orElseThrow(() ->
                            new RuntimeException("Serviço não encontrado"));

                servicos.add(servico);
            }
        }

        long diarias = ChronoUnit.DAYS.between(entrada, saida);

        BigDecimal total = BigDecimal.ZERO;

        for (Quarto quarto : quartos) {
            total = total.add(
                quarto.getValorDiaria()
                    .multiply(BigDecimal.valueOf(diarias))
            );
        }

        // Cada serviço é cobrado uma vez por reserva
        for (Servico servico : servicos) {
            total = total.add(servico.getPreco());
        }

        Reserva reserva = new Reserva();

        reserva.setHospede(hospede);
        reserva.setQuartos(quartos);
        reserva.setServicos(servicos);
        reserva.setDataEntrada(entrada);
        reserva.setDataSaida(saida);
        reserva.setValorTotal(total);
        reserva.setStatus("CONFIRMADA");

        return reservaRepository.save(reserva);
    }

    // LISTAR RESERVAS
    @Transactional(readOnly = true)
    public List<Reserva> listar() {

        List<Reserva> reservas = reservaRepository.findAll();

        for (Reserva reserva : reservas) {
            reserva.getQuartos().size();
            reserva.getServicos().size();
        }

        return reservas;
    }

    // BUSCAR RESERVA PELO ID
    @Transactional(readOnly = true)
    public Reserva buscarPorId(Long id) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Reserva não encontrada"));

        reserva.getQuartos().size();
        reserva.getServicos().size();

        return reserva;
    }

    // CANCELAR RESERVA
    @Transactional
    public Reserva cancelar(Long id) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Reserva não encontrada"));

        if ("CANCELADA".equals(reserva.getStatus())) {
            throw new IllegalStateException(
                "A reserva já está cancelada"
            );
        }

        reserva.setStatus("CANCELADA");

        // Carrega as relações antes de retornar
        reserva.getQuartos().size();
        reserva.getServicos().size();

        return reservaRepository.save(reserva);
    }
}