package br.com.thander.sonda.repository;

import br.com.thander.sonda.model.entity.SondaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SondaRepository extends JpaRepository<SondaEntity, Long> {
    
    /***
     * Busca uma sonda pela localização de uma coordenada específica
     * @param planeta String com nome do planeta em letras maiúsculas
     * @param x Integer com a posição X de uma coordenada
     * @param y Integer com a posição Y de uma coordenada
     * @return Optional contento um objeto SondaEntity ou Optional.empty caso não exista sonda na coordenada
     */
    Optional<SondaEntity> findByPlanetaAndAtualXAndAtualY(String planeta, Integer x, Integer y);
}
