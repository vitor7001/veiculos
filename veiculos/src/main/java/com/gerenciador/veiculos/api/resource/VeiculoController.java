package com.gerenciador.veiculos.api.resource;

import com.gerenciador.veiculos.api.dto.VeiculoDTO;
import com.gerenciador.veiculos.model.Veiculo;
import com.gerenciador.veiculos.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

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
    public VeiculoDTO criar(@RequestBody @Valid Veiculo dto){

        Veiculo veiculo = modelMapper.map(dto, Veiculo.class);

        veiculo = veiculoService.salvar(veiculo);

        return new VeiculoDTO();
    }

    @GetMapping("{id}")
    public VeiculoDTO buscar(@PathVariable Long id){

        return veiculoService.buscarPorId(id).map(veiculo -> modelMapper.map(veiculo, VeiculoDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id){

        Veiculo veiculo = veiculoService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        veiculoService.excluir(veiculo);
    }

    @PutMapping("{id}")
    public VeiculoDTO atualizar(@PathVariable Long id, @RequestBody @Valid VeiculoDTO dto){

        Veiculo veiculo = veiculoService.buscarPorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        veiculo.setChassi(dto.getChassi());
        veiculo.setName(dto.getName());
        veiculo.setManufacturer(dto.getManufacturer());
        veiculo.setYear(dto.getYear());
        veiculo.setColor(dto.getColor());
        veiculo.setStatus(dto.getStatus());
        veiculo.setPlaca(dto.getPlaca());

        veiculo = veiculoService.atualizar(veiculo);

        return modelMapper.map(veiculo, VeiculoDTO.class);
    }

}
