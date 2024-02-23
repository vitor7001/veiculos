package com.gerenciador.veiculos.service.implementacao;

import com.gerenciador.veiculos.exception.BusinessException;
import com.gerenciador.veiculos.model.Veiculo;
import com.gerenciador.veiculos.model.repository.VeiculoRepository;
import com.gerenciador.veiculos.service.VeiculoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VeiculoServiceImplementacao implements VeiculoService {

    private VeiculoRepository veiculoRepository;

    public VeiculoServiceImplementacao(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @Override
    public Veiculo salvar(Veiculo veiculo) {

        if(veiculoRepository.existsByChassi(veiculo.getChassi())){
            throw new BusinessException("Este chassi já existe na base.");
        }

        if(veiculoRepository.existsByPlaca(veiculo.getPlaca())){
            throw new BusinessException("Esta placa já existe na base.");
        }

        return this.veiculoRepository.save(veiculo);
    }

    @Override
    public Optional<Veiculo> buscarPorId(Long id) {

        return this.veiculoRepository.findById(id);

    }

    @Override
    public void delecaoLogicaVeiculo(Veiculo veiculo) {
        //não está sendo apagado o registro do veiculo do banco de dados, apenas alterado o status dele para "DEACTIVATED"
        veiculoRepository.save(veiculo);
    }

    @Override
    public Veiculo atualizar(Veiculo veiculo) {

        if (veiculo == null){
            throw  new BusinessException("O id não pode ser nulo.");
        }

        return  this.veiculoRepository.save(veiculo);
    }

    @Override
    public Page<Veiculo> listarVeiculos(Veiculo filter, Pageable pageRequest) {

        Example<Veiculo> example = Example.of(filter, ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnorePaths("id"));

        return veiculoRepository.findAll(example, pageRequest);
    }


}

