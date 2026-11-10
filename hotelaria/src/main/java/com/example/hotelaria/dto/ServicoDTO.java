package com.example.hotelaria.dto;

import java.math.BigDecimal;

public record ServicoDTO(
    Long id,
    String nome,
    String descricao,
    BigDecimal preco
) {}