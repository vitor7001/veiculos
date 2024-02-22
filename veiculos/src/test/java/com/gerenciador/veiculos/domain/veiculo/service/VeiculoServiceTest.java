package com.gerenciador.veiculos.domain.veiculo.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.gerenciador.veiculos.exception.BusinessException;
import com.gerenciador.veiculos.model.Veiculo;
import com.gerenciador.veiculos.model.repository.VeiculoRepository;
import com.gerenciador.veiculos.service.VeiculoService;
import com.gerenciador.veiculos.service.implementacao.VeiculoServiceImplementacao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class VeiculoServiceTest {

    VeiculoService veiculoService;

    @MockBean
    VeiculoRepository veiculoRepository;

    @BeforeEach
    public void setup() {
        veiculoService = new VeiculoServiceImplementacao(veiculoRepository);
    }

    @Test
    @DisplayName("Deve salvar um veiculo")
    public void salvarVeiculoTest() {

        Veiculo veiculo = criarVeiculo();

        Mockito.when(veiculoRepository.existsByChassi(Mockito.anyString())).thenReturn(false);
        Mockito.when(veiculoRepository.existsByPlaca(Mockito.anyString())).thenReturn(false);

        Mockito.when(veiculoRepository.save(veiculo))
                .thenReturn(Veiculo.builder()
                        .id(1L)
                        .chassi("QualquerChassi")
                        .name("QualquerNome")
                        .manufacturer("QualquerManufacturer")
                        .year(2024)
                        .color("White")
                        .status("ACTIVATED")
                        .placa("QualquerPlaca").build());

        Veiculo veiculoSalvo = veiculoService.salvar(veiculo);

        assertThat(veiculoSalvo.getId()).isNotNull();
        assertThat(veiculoSalvo.getChassi()).isNotNull();
        assertThat(veiculoSalvo.getName()).isNotNull();
        assertThat(veiculoSalvo.getManufacturer()).isNotNull();
        assertThat(veiculoSalvo.getYear()).isNotNull();
        assertThat(veiculoSalvo.getColor()).isNotNull();
        assertThat(veiculoSalvo.getStatus()).isNotNull();
        assertThat(veiculoSalvo.getPlaca()).isNotNull();

    }


    @Test
    @DisplayName("Deve lançar um erro ao tentar salvar um veiculo com um chassi já cadastrado")
    public void erroAoSalvarVeiculoComChassiDuplicado(){

        Veiculo veiculo = criarVeiculo();

        Mockito.when(veiculoRepository.existsByChassi(Mockito.anyString())).thenReturn(true);

        Throwable excecao = Assertions.catchThrowable(() -> veiculoService.salvar(veiculo));

        assertThat(excecao).isInstanceOf(BusinessException.class).hasMessage("Este chassi já existe na base.");

        Mockito.verify(veiculoRepository, Mockito.never()).save(veiculo);
    }

    @Test
    @DisplayName("Deve lançar um erro ao tentar salvar um veiculo com uma placa já cadastrada")
    public void erroAoSalvarVeiculoComPlacaDuplicada(){

        Veiculo veiculo = criarVeiculo();

        Mockito.when(veiculoRepository.existsByPlaca(Mockito.anyString())).thenReturn(true);

        Throwable excecao = Assertions.catchThrowable(() -> veiculoService.salvar(veiculo));

        assertThat(excecao).isInstanceOf(BusinessException.class).hasMessage("Esta placa já existe na base.");

        Mockito.verify(veiculoRepository, Mockito.never()).save(veiculo);
    }

    public static Veiculo criarVeiculo() {
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
