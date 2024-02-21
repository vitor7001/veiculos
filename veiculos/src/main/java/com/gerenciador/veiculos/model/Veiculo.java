package com.gerenciador.veiculos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {

    private long id;
    private String chassi;
    private String name;
    private String manufacturer;
    private int year;
    private String color;
    private String status;
    private String placa;

}
