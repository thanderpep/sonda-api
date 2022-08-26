package br.com.thander.sonda.service;

import br.com.thander.sonda.exception.ColisaoException;
import br.com.thander.sonda.model.dto.ComandoDTO;
import br.com.thander.sonda.model.dto.RetornoDTO;
import br.com.thander.sonda.model.dto.SondaDTO;
import br.com.thander.sonda.model.entity.CoordenadaEntity;
import br.com.thander.sonda.model.entity.SondaEntity;
import br.com.thander.sonda.repository.SondaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Service
public class SondaService {
    
    @Autowired
    private SondaRepository sondaRepository;
    
    /***
     * Cria uma nova sonda e movimenta ela caso uma sequência de comandos seja informada
     * @param sondaDTO
     * @return RetornoDTO com as informações da sonda e as coordenadas de todos os movimentos realizados por ela
     */
    public RetornoDTO criaSonda(SondaDTO sondaDTO) {
        SondaEntity sondaEntity = sondaDTO.converteParaSondaEntity();
        
        // Verifica se existe sonda parada na coordenada de pouso
        try {
            verificaColisaoSonda(sondaEntity.coordenadaAtual());
        } catch (ColisaoException ex) {
            return sondaEntity.converteParaRetornoColisaoPouso(ex.getMessage() + " Não é possível pousar neste ponto.");
        }
        
        // Salva no banco de dados o estado inicial da sonda
        sondaRepository.saveAndFlush(sondaEntity);
        log.info(String.format("Posição de pouso da sonda %d: %s", sondaEntity.getId(), sondaEntity.coordenadaAtual()));
        
        try {
            executaComandos(sondaEntity, sondaDTO.getComandos());
            log.info(String.format("Posição final da sonda %d: %s", sondaEntity.getId(), sondaEntity.coordenadaAtual()));
            return sondaEntity.converteParaRetornoSucesso();
        } catch (ColisaoException ex) {
            String erro = String.format("%s Não é possível mover para este ponto. A sonda %d ficou parada nas " +
                    "coordenadas %s", ex.getMessage(), sondaEntity.getId(), sondaEntity.coordenadaAtual());
            return sondaEntity.converteParaRetornoColisaoMovimento(erro);
        }
    }
    
    /***
     * Verifica se a coordenada informada está ocupada por outra sonda no mesmo planeta
     * @param coordenada
     * @throws ColisaoException
     */
    private void verificaColisaoSonda(CoordenadaEntity coordenada) throws ColisaoException {
        Optional<SondaEntity> sonda = sondaRepository.findByPlanetaAndAtualXAndAtualY(
                coordenada.getSonda().getPlaneta(), coordenada.getX(), coordenada.getY());
        if (sonda.isPresent() && sonda.get().getId() != coordenada.getSonda().getId()) {
            throw new ColisaoException(coordenada);
        }
    }
    
    /***
     * Executa uma sequência de comandos para mover uma sonda
     * @param sondaEntity SondaEntity
     * @param comandos String de sequência de comandos
     * @return SondaEntity
     */
    private SondaEntity executaComandos(SondaEntity sondaEntity, String comandos) throws ColisaoException {
        if (comandos != null && !comandos.isEmpty()) {
            log.info(String.format("Sequência de comandos: %s", comandos));
            sondaEntity.setUltimoComando(comandos);
            
            // itera os comandos para movimentar a sonda
            for (char comando : comandos.toCharArray()) {
                CoordenadaEntity proxCoordenada = sondaEntity.coordenadaAtual().calculaProximaCoordenada(comando);
                verificaColisaoSonda(proxCoordenada);
                sondaEntity.mover(comando);
                sondaRepository.saveAndFlush(sondaEntity);
            }
        } else {
            log.info(String.format("Sonda %d - Nenhum comando a executar.", sondaEntity.getId()));
        }
        return sondaEntity;
    }
    
    /***
     * Busca as informações atuais da sonda
     * @param sondaId
     * @return RetornoDTO com as informações da sonda e as coordenadas de todos os movimentos realizados por ela
     */
    public RetornoDTO buscaSondaPorId(Long sondaId) {
        return sondaRepository.findById(sondaId)
                .map(sonda -> sonda.converteParaRetornoSucesso())
                .orElseThrow(() -> new EntityNotFoundException("Sonda não encontrada"));
    }
    
    /***
     * Movimenta uma sonda já existente através de uma sequência de comandos
     * @param sondaId
     * @param comandos
     * @return RetornoDTO com as informações da sonda e as coordenadas dos movimentos realizados pela sequencia
     * de comandos recebida
     */
    public RetornoDTO movimentaSondaPorId(Long sondaId, ComandoDTO comandos) {
        return sondaRepository.findById(sondaId)
                .map(sonda -> {
                    try {
                        log.info(String.format("Posição atual da sonda %d: %s", sonda.getId(), sonda.coordenadaAtual()));
                        int index = sonda.getCoordenadas().indexOf(sonda.coordenadaAtual());
                        executaComandos(sonda, comandos.getComandos());
                        log.info(String.format("Posição final da sonda %d: %s", sonda.getId(), sonda.coordenadaAtual()));
                        return sonda.converteParaRetornoSucessoPut(index);
                    } catch (ColisaoException ex) {
                        String erro = String.format("%s Não é possível mover para este ponto. A sonda %d ficou parada nas coordenadas %s",
                                ex.getMessage(), sonda.getId(), sonda.coordenadaAtual());
                        return sonda.converteParaRetornoColisaoMovimento(erro);
                    }
                }).orElseThrow(() -> new EntityNotFoundException("Sonda não encontrada"));
    }
}
