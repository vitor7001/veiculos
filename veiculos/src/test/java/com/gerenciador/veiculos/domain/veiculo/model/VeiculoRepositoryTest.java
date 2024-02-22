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
    @DisplayName("Deve retornar verdadeiro quando existir um veiculo com o chassi informado.")
    public void deveBuscarVeiculoPeloChassiTest() {
        String chassi = "loreipsumdolor";

        Veiculo veiculo = criarVeiculo(chassi, "QualquerPlaca");

        tem.persist(veiculo);

        boolean exists = veiculoRepository.existsByChassi(chassi);

        assertThat(exists).isTrue();
        assertThat(chassi).isEqualTo(veiculo.getChassi());
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um veiculo com a placa informado.")
    public void deveBuscarVeiculoPelaPlacaTest() {
        String placa = "OPA0148";

        Veiculo veiculo = criarVeiculo("QualquerChassi", placa);

        tem.persist(veiculo);

        boolean exists = veiculoRepository.existsByPlaca(placa);

        assertThat(exists).isTrue();
        assertThat(placa).isEqualTo(veiculo.getPlaca());
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um veiculo com o id informado.")
    public void deveSalvarUmVeiculoTest(){
        //arrange
        Veiculo veiculo = criarVeiculo("QualquerChassi", "QualquerPlaca");

        //act
        Veiculo veiculoSalvo = veiculoRepository.save(veiculo);

        //assert
        assertThat(veiculoSalvo.getId()).isNotNull();
    }


    @Test
    @DisplayName("Deve obter um veiculo por id")
    public void buscarPorIdTest() {
        //arrange
        Veiculo veiculo = criarVeiculo("QualquerChassi", "QualquerPlaca");
        tem.persist(veiculo);

        //act
        Optional<Veiculo> foundBook = veiculoRepository.findById(veiculo.getId());

        //assert
        assertThat(foundBook.isPresent()).isTrue();
    }

    public static Veiculo criarVeiculo(String chassi, String placa){
        return Veiculo.builder()
                .chassi(chassi)
                .name("QualquerNome")
                .manufacturer("QualquerManufacturer")
                .year(2024)
                .color("White")
                .status("ACTIVATED")
                .placa(placa).build();
    }

}
