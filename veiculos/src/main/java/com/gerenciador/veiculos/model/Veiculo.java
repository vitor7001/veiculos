package com.gerenciador.veiculos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Veiculo {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String chassi;
    @Column
    private String name;
    @Column
    private String manufacturer;
    @Column
    private int year;
    @Column
    private String color;
    @Column
    private String status;
    @Column
    private String placa;

}