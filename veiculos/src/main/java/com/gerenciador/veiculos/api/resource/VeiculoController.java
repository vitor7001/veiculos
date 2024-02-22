package com.gerenciador.veiculos.api.resource;

import com.gerenciador.veiculos.api.dto.VeiculoDTO;
import com.gerenciador.veiculos.model.Veiculo;
import com.gerenciador.veiculos.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoDTO criar(@RequestBody @Valid Veiculo dto){

        return new VeiculoDTO();
    }

}
