package com.gerenciador.veiculos.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoStatusDTO {

    @NotBlank(message = "O campo status não pode ser nulo")
    private String status;

}
