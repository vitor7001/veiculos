package com.gerenciador.veiculos.service.implementacao;

import com.gerenciador.veiculos.exception.BusinessException;
import com.gerenciador.veiculos.model.Veiculo;
import com.gerenciador.veiculos.model.repository.VeiculoRepository;
import com.gerenciador.veiculos.service.VeiculoService;
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

        if (veiculoRepository.existsById(veiculo.getId())) {
            throw new BusinessException("Este id já existe na base.");
        }

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
    public Page<Veiculo> listarVeiculos(Veiculo filter, Pageable pageRequest) {
        return null;
    }


}

