package br.com.thander.sonda.service;

import br.com.thander.sonda.model.dto.EntradaSondaDTO;
import br.com.thander.sonda.model.dto.RetornoSondaDTO;
import br.com.thander.sonda.model.entity.CoordenadaEntity;
import br.com.thander.sonda.model.entity.SondaEntity;
import br.com.thander.sonda.repository.SondaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SondaService {
    
    @Autowired
    private SondaRepository sondaRepository;
    
    public List<RetornoSondaDTO> criaSonda(List<EntradaSondaDTO> entradaSondaDTOLista) {
        List<RetornoSondaDTO> retornoSondaLista = new ArrayList<>();
        
        for (EntradaSondaDTO sondaDTO : entradaSondaDTOLista) {
            SondaEntity sondaEntity = sondaDTO.converteParaSondaEntity();
            
            // Verifica se existe sonda parada na coordenada de pouso
            RetornoSondaDTO retorno = verificaColisao(sondaEntity.coordenadaAtual());
            
            if (retorno != null) {
                retornoSondaLista.add(retorno);
                continue;
            }
            
            // Salva no banco de dados o estado inicial da sonda
            sondaEntity = sondaRepository.saveAndFlush(sondaEntity);
            log.info(String.format("Posição de pouso da sonda %d: %s", sondaEntity.getId(), sondaEntity.coordenadaAtual()));
            
            retorno = executaComandos(sondaEntity, sondaDTO.getComandos());
            retornoSondaLista.add(retorno);
            
            log.info(String.format("Posição final da sonda %d: %s", sondaEntity.getId(), sondaEntity.coordenadaAtual()));
        }
        return retornoSondaLista;
    }
    
    /***
     * Verifica se a coordenada informada está ocupada por outra sonda no mesmo planeta
     * @param coordenada CoordenadaEntity
     * @return RetornoSondaDTO com dados sobre colisão de sondas ou null caso a coordenada
     * não esteja ocupada por outra sonda
     */
    private RetornoSondaDTO verificaColisao(CoordenadaEntity coordenada) {
        Optional<SondaEntity> sonda = sondaRepository.findByPlanetaAndAtualXAndAtualY(
                coordenada.getSonda().getPlaneta(), coordenada.getX(), coordenada.getY());
        
        if (sonda.isPresent() && sonda.get().getId() != coordenada.getSonda().getId()) {
            StringBuffer erro = new StringBuffer(String.format(
                    "As coordenadas x=%d, y=%d já estão ocupadas pela sonda %d. ",
                    coordenada.getX(), coordenada.getY(), sonda.get().getId()));
            
            if (coordenada.getSonda().getId() == null) {
                erro.append("Não é possível pousar neste ponto.");
                return coordenada.getSonda().converteParaRetornoColisaoPouso(erro.toString());
            } else {
                erro.append(String.format("Não é possível mover para este ponto. A sonda %d ficou parada nas " +
                        "coordenadas %s", coordenada.getSonda().getId(), coordenada.getSonda().coordenadaAtual()));
                return coordenada.getSonda().converteParaRetornoColisaoMovimento(erro.toString());
            }
        }
        return null;
    }
    
    /***
     * Executa uma sequência de comandos para mover uma sonda
     * @param sondaEntity SondaEntity
     * @param comandos String de sequência de comandos
     * @return RetornoSondaDTO com dados sobre colisão de sondas ou RetornoSondaDTO com dados de sucesso
     * e posições da sonda
     */
    private RetornoSondaDTO executaComandos(SondaEntity sondaEntity, String comandos) {
        RetornoSondaDTO retorno;
        if (comandos != null && !comandos.isEmpty()) {
            log.info(String.format("Sequência de comandos: %s", comandos));
            
            // itera os comandos para movimentar a sonda
            for (char comando : comandos.toCharArray()) {
                CoordenadaEntity proxCoordenada = sondaEntity.coordenadaAtual().calculaProximaCoordenada(comando);
                retorno = verificaColisao(proxCoordenada);
                
                if (retorno != null)
                    return retorno;
                
                sondaEntity.mover(comando);
                sondaRepository.saveAndFlush(sondaEntity);
            }
        } else {
            log.info(String.format("Sonda %d - Nenhum comando a executar.", sondaEntity.getId()));
        }
        return sondaEntity.converteParaRetornoSondaSucesso();
    }
}
