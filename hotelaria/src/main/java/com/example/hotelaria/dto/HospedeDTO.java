package com.example.hotelaria.dto;

public record HospedeDTO(
    Long id,
    String nome,
    String cpf,
    String email,
    String telefone
) {}