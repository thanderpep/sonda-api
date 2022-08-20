package br.com.thander.sonda.repository;

import br.com.thander.sonda.model.entity.HistoricoCoordenadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordenadaRepository extends JpaRepository<HistoricoCoordenadaEntity, Long> {
}
