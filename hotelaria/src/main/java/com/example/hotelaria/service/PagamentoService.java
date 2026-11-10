package com.example.hotelaria.service;

import com.example.hotelaria.model.*;
import com.example.hotelaria.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final ReservaRepository reservaRepository;

    public PagamentoService(
            PagamentoRepository pagamentoRepository,
            ReservaRepository reservaRepository) {

        this.pagamentoRepository = pagamentoRepository;
        this.reservaRepository = reservaRepository;
    }

    @Transactional
    public Pagamento cadastrar(
            Long reservaId,
            BigDecimal valor,
            String formaPagamento) {

        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() ->
                    new RuntimeException("Reserva não encontrada"));

        if ("CANCELADA".equals(reserva.getStatus())) {
            throw new IllegalStateException(
                "Não é possível pagar uma reserva cancelada"
            );
        }

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                "O valor deve ser positivo"
            );
        }

        if (formaPagamento == null ||
                formaPagamento.isBlank()) {
            throw new IllegalArgumentException(
                "Informe a forma de pagamento"
            );
        }

        BigDecimal totalPago = pagamentoRepository
                .findByReservaId(reservaId)
                .stream()
                .filter(p -> "APROVADO".equals(p.getStatus()))
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPago.add(valor)
                .compareTo(reserva.getValorTotal()) > 0) {
            throw new IllegalArgumentException(
                "Pagamento excede o valor restante"
            );
        }

        Pagamento pagamento = new Pagamento();

        pagamento.setReserva(reserva);
        pagamento.setValor(valor);
        pagamento.setFormaPagamento(formaPagamento);
        pagamento.setDataPagamento(LocalDate.now());
        pagamento.setStatus("APROVADO");

        return pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> listar() {
        return pagamentoRepository.findAll();
    }

    public Pagamento buscarPorId(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Pagamento não encontrado"));
    }
}