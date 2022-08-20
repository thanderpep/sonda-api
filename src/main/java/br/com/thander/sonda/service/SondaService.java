package br.com.thander.sonda.service;

import br.com.thander.sonda.model.dto.SondaDTO;
import br.com.thander.sonda.model.entity.SondaEntity;
import br.com.thander.sonda.repository.SondaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SondaService {
    
    @Autowired
    private SondaRepository sondaRepository;
    
    public SondaDTO lancaSonda(SondaDTO sondaDTO) {
        // Salva no banco de dados o estado inicial da sonda
        SondaEntity sondaEntity = sondaRepository.saveAndFlush(sondaDTO.converteParaSondaEntity());
        log.info(String.format("Posição de pouso da sonda %d: %s", sondaEntity.getId(), sondaEntity.capturaCoordenadaAtual().toString()));
        
        executaComandos(sondaEntity, sondaDTO.getComandos());
        
        log.info(String.format("Posição final da sonda %d: %s", sondaEntity.getId(), sondaEntity.capturaCoordenadaAtual().toString()));
        return sondaDTO;
    }
    
    private void executaComandos(SondaEntity sondaEntity, String comandos) {
        if (comandos != null && !comandos.isEmpty()) {
            log.info(String.format("Sequência de comandos: %s", comandos));
            // itera os comandos para movimentar a sonda
            for (char comando : comandos.toCharArray()) {
                sondaEntity.movimentaSonda(comando);
            }
            sondaRepository.saveAndFlush(sondaEntity);
        } else {
            log.info(String.format("Sonda %d - Nenhum comando a executar.", sondaEntity.getId()));
        }
    }
}
