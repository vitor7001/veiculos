package com.gerenciador.veiculos.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoFiltroDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String manufacturer;

    @NotEmpty
    private int year;

}
