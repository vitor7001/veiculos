package com.gerenciador.veiculos.service.implementacao;

import com.gerenciador.veiculos.model.Veiculo;
import com.gerenciador.veiculos.service.VeiculoService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VeiculoServiceImplementacao implements VeiculoService {


    @Override
    public Veiculo salvar(Veiculo veiculo) {
        return null;
    }

    @Override
    public Optional<Veiculo> buscarPorId(Long id) {
        return Optional.empty();
    }

    @Override
    public void excluir(Veiculo veiculo) {

    }

    @Override
    public Veiculo atualizar(Veiculo veiculo) {
        return null;
    }

    @Override
    public Page<Veiculo> listarVeiculos(String name, String manufacturer, int ano) {
        return null;
    }
}

