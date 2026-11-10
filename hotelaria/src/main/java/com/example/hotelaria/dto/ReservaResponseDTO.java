package com.example.hotelaria.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ReservaResponseDTO(
    Long id,
    Long hospedeId,
    List<Long> quartosIds,
    List<Long> servicosIds,
    LocalDate dataEntrada,
    LocalDate dataSaida,
    String status,
    BigDecimal valorTotal
) {}