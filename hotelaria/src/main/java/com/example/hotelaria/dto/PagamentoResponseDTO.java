package com.example.hotelaria.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoResponseDTO(
    Long id,
    Long reservaId,
    BigDecimal valor,
    LocalDate dataPagamento,
    String formaPagamento,
    String status
) {}