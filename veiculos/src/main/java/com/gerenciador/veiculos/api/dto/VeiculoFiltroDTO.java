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

    private String name;

    private String manufacturer;

    private int year;

}
