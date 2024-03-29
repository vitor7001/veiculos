package com.gerenciador.veiculos.domain.veiculo.api.resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerenciador.veiculos.api.dto.VeiculoDTO;
import com.gerenciador.veiculos.api.dto.VeiculoStatusDTO;
import com.gerenciador.veiculos.api.resource.VeiculoController;
import com.gerenciador.veiculos.exception.BusinessException;
import com.gerenciador.veiculos.model.Veiculo;
import com.gerenciador.veiculos.service.VeiculoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = VeiculoController.class)
@AutoConfigureMockMvc
public class VeiculoControllerTest {

    static String API_BASE_URL = "/api/veiculos";

    @Autowired
    MockMvc mvc;

    @MockBean
    VeiculoService veiculoService;

    @Test
    @DisplayName("Deve criar um veiculo")
    public void criarVeiculo() throws Exception {

        VeiculoDTO dto = criarVeiculoDTO();

        Veiculo veiculoSalvo = Veiculo.builder().id(1L).name("Uno").chassi("SIMNAO").status("ACTIVATED").year(2024).placa("QWE123S").color("RED").manufacturer("FIAT").build();

        BDDMockito.given(veiculoService.salvar(Mockito.any(Veiculo.class))).willReturn(veiculoSalvo);

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder requisicao = MockMvcRequestBuilders.post(API_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

        mvc.perform(requisicao).andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("chassi").isNotEmpty())
                .andExpect(jsonPath("name").isNotEmpty())
                .andExpect(jsonPath("manufacturer").isNotEmpty())
                .andExpect(jsonPath("year").isNotEmpty())
                .andExpect(jsonPath("color").isNotEmpty())
                .andExpect(jsonPath("status").isNotEmpty())
                .andExpect(jsonPath("placa").isNotEmpty());
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não houver dados suficientes para criação do veiculo.")
    public void erroAoCriarVeiculoFaltandoPropriedades() throws Exception {
        String json = new ObjectMapper().writeValueAsString(new VeiculoDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

        mvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("erros", hasSize(7)));
    }


    @Test
    @DisplayName("Deve lançar erro ao tentar criar um veiculo com chassi já cadastrado.")
    public void erroAoCriarVeiculoComChassiDuplicado() throws Exception {

        VeiculoDTO dto = criarVeiculoDTO();
        String json = new ObjectMapper().writeValueAsString(dto);

        String mensagemDeErro = "Este chassi já existe na base.";

        BDDMockito.given(veiculoService.salvar(Mockito.any(Veiculo.class))).willThrow(new BusinessException(mensagemDeErro));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

        mvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("erros", hasSize(1)))
                .andExpect(jsonPath("erros[0]").value(mensagemDeErro));

    }


    @Test
    @DisplayName("Deve lançar erro ao tentar criar um veiculo com placa já cadastrado.")
    public void erroAoCriarVeiculoComPlacaDuplicada() throws Exception {

        VeiculoDTO dto = criarVeiculoDTO();
        String json = new ObjectMapper().writeValueAsString(dto);

        String mensagemDeErro = "Esta placa já existe na base.";

        BDDMockito.given(veiculoService.salvar(Mockito.any(Veiculo.class))).willThrow(new BusinessException(mensagemDeErro));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

        mvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("erros", hasSize(1)))
                .andExpect(jsonPath("erros[0]").value(mensagemDeErro));

    }

    @Test
    @DisplayName("Deve lançar erro ao criar veiculo com Status fora dos padrões")
    public void erroAoCriarVeiculoComStatusInvalidos() throws Exception {
        String status = "ERRORSTATUSTEST";
        String mensagemDeErro = "O Status " + status + " não corresponde a nenhum dos status validos.";

        VeiculoDTO dto = criarVeiculoDTO();
        dto.setStatus(status);
        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

        mvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("erros", hasSize(1)))
                .andExpect(jsonPath("erros[0]").value(mensagemDeErro));

    }

    @Test
    @DisplayName("Deve retornar resource not found quando não encontrar o livro para deletar")
    public void deleteInexistentBookTest() throws Exception {

        BDDMockito.given(veiculoService.buscarPorId(Mockito.anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API_BASE_URL.concat("/" + 1));

        mvc.perform(request).andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Deve buscar um veiculo.")
    public void buscarveiculoPorId() throws Exception {
        Long id = 1l;

        VeiculoDTO dto = criarVeiculoDTO();

        Veiculo veiculo = Veiculo.builder().id(1L).name(criarVeiculoDTO().getName())
                .chassi(criarVeiculoDTO().getChassi())
                .status(criarVeiculoDTO().getStatus())
                .year(criarVeiculoDTO().getYear())
                .placa(criarVeiculoDTO().getPlaca())
                .color(criarVeiculoDTO().getColor())
                .manufacturer(criarVeiculoDTO().getManufacturer()).build();

        BDDMockito.given(veiculoService.buscarPorId(id)).willReturn(Optional.of(veiculo));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API_BASE_URL.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("name").value(criarVeiculoDTO().getName()))
                .andExpect(jsonPath("chassi").value(criarVeiculoDTO().getChassi()))
                .andExpect(jsonPath("status").value(criarVeiculoDTO().getStatus()))
                .andExpect(jsonPath("year").value(criarVeiculoDTO().getYear()))
                .andExpect(jsonPath("placa").value(criarVeiculoDTO().getPlaca()))
                .andExpect(jsonPath("color").value(criarVeiculoDTO().getColor()))
                .andExpect(jsonPath("manufacturer").value(criarVeiculoDTO().getManufacturer()));

    }


    @Test
    @DisplayName("Deve atualizar um veiculo com um status RESERVED")
    public void atualizarVeiculoStatusValido() throws Exception {
        Long id = 1l;

        String json = new ObjectMapper().writeValueAsString(criarVeiculoStatusDTO());

        Veiculo veiculo = Veiculo.builder().id(1L).name("Uno").chassi("SIMNAO").status("ACTIVATED").year(2024).placa("QWE123S").color("RED").manufacturer("FIAT").build();

        BDDMockito.given(veiculoService.buscarPorId(id)).willReturn(Optional.of(veiculo));

        Veiculo veiculoAtualizado =Veiculo.builder().id(1L).name("Uno").chassi("SIMNAO").status("RESERVED").year(2024).placa("QWE123S").color("RED").manufacturer("FIAT").build();
        BDDMockito.given(veiculoService.atualizar(veiculo)).willReturn(veiculoAtualizado);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch(API_BASE_URL.concat("/" + 1)).content(json)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("name").value(criarVeiculoDTO().getName()))
                .andExpect(jsonPath("chassi").value(criarVeiculoDTO().getChassi()))
                .andExpect(jsonPath("manufacturer").value(criarVeiculoDTO().getManufacturer()))
                .andExpect(jsonPath("status").value("RESERVED"));
    }

    public VeiculoDTO criarVeiculoDTO(){
        return VeiculoDTO.builder().name("Uno").chassi("SIMNAO").status("ACTIVATED").year(2024).placa("QWE123S").color("RED").manufacturer("FIAT").build();
    }

    public VeiculoStatusDTO criarVeiculoStatusDTO(){
        return VeiculoStatusDTO.builder().status("ACTIVATED").build();
    }
}

