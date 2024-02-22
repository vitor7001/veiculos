package com.gerenciador.veiculos.domain.veiculo.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.gerenciador.veiculos.model.Veiculo;
import com.gerenciador.veiculos.model.repository.VeiculoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class VeiculoRepositoryTest{

    @Autowired
    TestEntityManager tem;

    @Autowired
    VeiculoRepository veiculoRepository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um veiculo com o id informado.")
    public void deveSalvarUmVeiculo(){
        //arrange
        Veiculo veiculo = criarVeiculo();

        //act
        Veiculo veiculoSalvo = veiculoRepository.save(veiculo);

        //assert
        assertThat(veiculoSalvo.getId()).isNotNull();
    }


    @Test
    @DisplayName("Deve obter um veiculo por id")
    public void findByIdTest() {
        //arrange
        Veiculo veiculo = criarVeiculo();
        tem.persist(veiculo);

        //act
        Optional<Veiculo> foundBook = veiculoRepository.findById(veiculo.getId());

        //assert
        assertThat(foundBook.isPresent()).isTrue();
    }

    public static Veiculo criarVeiculo(){
        return Veiculo.builder()
                .chassi("QualquerChassi")
                .name("QualquerNome")
                .manufacturer("QualquerManufacturer")
                .year(2024)
                .color("White")
                .status("ACTIVATED")
                .placa("QualquerPlaca").build();
    }

}
