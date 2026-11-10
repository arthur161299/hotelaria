package com.example.hotelaria.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record PagamentoRequestDTO(

    @NotNull
    Long reservaId,

    @NotNull
    @Positive
    BigDecimal valor,

    @NotBlank
    String formaPagamento

) {}