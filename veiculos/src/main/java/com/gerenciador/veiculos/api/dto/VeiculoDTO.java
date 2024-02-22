package com.gerenciador.veiculos.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoDTO {

    private long id;

    @NotBlank(message = "O campo chassi não pode ser nulo")
    private String chassi;

    @NotBlank(message = "O campo name não pode ser nulo")
    private String name;

    @NotBlank(message = "O campo menufacturer não pode ser nulo")
    private String manufacturer;

    @NotNull(message = "O campo year não pode ser nulo")
    @Positive(message = "O valor deve ser maior que zero")
    private int year;

    @NotBlank(message = "O campo color não pode ser nulo")
    private String color;

    @NotBlank(message = "O campo status não pode ser nulo")
    private String status;

    @NotBlank(message = "O campo placa não pode ser nulo")
    private String placa;
}
