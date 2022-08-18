package br.com.thander.sonda.service;

import br.com.thander.sonda.model.entity.SondaEntity;
import br.com.thander.sonda.repository.SondaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SondaService {
    
    @Autowired
    private SondaRepository sondaRepository;
    
    public SondaEntity create(SondaEntity sonda) {
        return sondaRepository.save(sonda);
    }
}
