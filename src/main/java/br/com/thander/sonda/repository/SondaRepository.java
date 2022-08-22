package br.com.thander.sonda.repository;

import br.com.thander.sonda.model.entity.SondaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SondaRepository extends JpaRepository<SondaEntity, Long> {
    
    Optional<SondaEntity> findByPlanetaAndAtualXAndAtualY(String planeta, Integer x, Integer y);
}
