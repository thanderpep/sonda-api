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
    
    public SondaEntity lancaSonda(SondaEntity sondaEntity) {
        // Salva no banco de dados o estado inicial da sonda
        sondaRepository.saveAndFlush(sondaEntity);
        
        // itera os comandos para movimentar a sonda
        for (char comando : sondaEntity.getComandos().toCharArray()) {
            sondaEntity.movimentaSonda(comando);
        }
        
        sondaRepository.saveAndFlush(sondaEntity);
        log.info(sondaEntity.ultimaCoordenada().toString());
        
        return sondaEntity;
    }
}
