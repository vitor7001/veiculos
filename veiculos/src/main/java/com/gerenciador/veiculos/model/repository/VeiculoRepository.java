package com.gerenciador.veiculos.model.repository;

import com.gerenciador.veiculos.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    boolean existsByChassi(String chassi);

    boolean existsByPlaca(String chassi);

}
