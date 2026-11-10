package com.example.hotelaria.dto;

import java.math.BigDecimal;

public record QuartoDTO(
    Long id,
    String numero,
    String tipo,
    BigDecimal valorDiaria,
    String status
) {}