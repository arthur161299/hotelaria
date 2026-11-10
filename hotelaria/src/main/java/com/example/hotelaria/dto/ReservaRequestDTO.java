package com.example.hotelaria.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public record ReservaRequestDTO(

    @NotNull
    Long hospedeId,

    @NotEmpty
    List<Long> quartosIds,

    List<Long> servicosIds,

    @NotNull
    LocalDate dataEntrada,

    @NotNull
    LocalDate dataSaida

) {}