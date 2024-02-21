package com.gerenciador.veiculos.service;

import com.gerenciador.veiculos.model.Veiculo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface VeiculoService {

    Veiculo salvar(Veiculo veiculo);

    Optional<Veiculo> buscarPorId(Long id);

    void excluir(Veiculo veiculo);

    Veiculo atualizar(Veiculo veiculo);

    Page<Veiculo> listarVeiculos(String name, String manufacturer, int ano);

}
