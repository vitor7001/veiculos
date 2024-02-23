package com.gerenciador.veiculos.api.resource;

import com.gerenciador.veiculos.api.dto.VeiculoDTO;
import com.gerenciador.veiculos.api.dto.VeiculoFiltroDTO;
import com.gerenciador.veiculos.api.dto.VeiculoStatusDTO;
import com.gerenciador.veiculos.exception.BusinessException;
import com.gerenciador.veiculos.exception.ListaVaziaException;
import com.gerenciador.veiculos.model.Veiculo;
import com.gerenciador.veiculos.service.VeiculoService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private VeiculoService veiculoService;
    private final ModelMapper modelMapper;

    public VeiculoController(VeiculoService veiculoService, ModelMapper modelMapper) {
        this.veiculoService = veiculoService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Criar um veículo")
    public VeiculoDTO criar(@RequestBody @Valid VeiculoDTO dto){

        if(!statusDentroDoEsperado(dto.getStatus())){
            throw new BusinessException("O Status " + dto.getStatus() + " não corresponde a nenhum dos status validos.");
        }

        Veiculo veiculo = modelMapper.map(dto, Veiculo.class);

        veiculo = veiculoService.salvar(veiculo);

        return modelMapper.map(veiculo, VeiculoDTO.class);
    }

    @GetMapping("{id}")
    @ApiOperation("Buscar um veículo")
    public VeiculoDTO buscar(@PathVariable Long id){
        return veiculoService.buscarPorId(id).map(veiculo -> modelMapper.map(veiculo, VeiculoDTO.class))
                .orElseThrow(() -> new ListaVaziaException("Não existe um veículo na base para ser pesquisado."));

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Deletar um veículo")
    public void deletacaoLogica(@PathVariable Long id){
        Veiculo veiculo = veiculoService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        veiculo.setStatus("DEACTIVATED");
        veiculoService.delecaoLogicaVeiculo(veiculo);
    }

    @PatchMapping("{id}")
    @ApiOperation("Atualizar o status de um veículo")
    public VeiculoDTO atualizar(@PathVariable Long id, @RequestBody @Valid VeiculoStatusDTO dto){

        Veiculo veiculo = veiculoService.buscarPorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!statusDentroDoEsperado(dto.getStatus())){
            throw new BusinessException("O Status " + dto.getStatus() + " não corresponde a nenhum dos status validos.");
        }

        veiculo.setStatus(dto.getStatus());

        veiculo = veiculoService.atualizar(veiculo);

        return modelMapper.map(veiculo, VeiculoDTO.class);
    }

    @GetMapping
    @ApiOperation("Buscar veiculos de forma paginada")
    public Page<VeiculoDTO> buscarPaginado(VeiculoFiltroDTO dto, Pageable pageRequest){

        Veiculo filtro = modelMapper.map(dto, Veiculo.class);

        Page<Veiculo> resultado = veiculoService.listarVeiculos(filtro, pageRequest);

        List<VeiculoDTO> listaDeVeiculosFiltrados = resultado.getContent().stream().map(entidade -> modelMapper.map(entidade, VeiculoDTO.class))
                .collect(Collectors.toList());

        if(listaDeVeiculosFiltrados.isEmpty()){
            throw new ListaVaziaException("Não existem veículos na base para serem pesquisados");
        }

        return new PageImpl<VeiculoDTO>(listaDeVeiculosFiltrados, pageRequest, resultado.getTotalElements());
    }


    private boolean statusDentroDoEsperado(String status){
        return "ACTIVATED".equals(status)
                || "DEACTIVATED".equals(status)
                || "RESERVED".equals(status)
                || "RENTED".equals(status);
    }

}
