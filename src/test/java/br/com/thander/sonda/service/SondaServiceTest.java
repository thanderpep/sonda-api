package br.com.thander.sonda.service;

import br.com.thander.sonda.ApplicationTestConfig;
import br.com.thander.sonda.model.dto.ComandoDTO;
import br.com.thander.sonda.model.dto.RetornoDTO;
import br.com.thander.sonda.model.dto.SondaDTO;
import br.com.thander.sonda.model.entity.CoordenadaEntity;
import br.com.thander.sonda.model.entity.SondaEntity;
import br.com.thander.sonda.repository.SondaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;

@DisplayName("SondaServiceTest")
public class SondaServiceTest extends ApplicationTestConfig {
    
    @MockBean
    private SondaRepository sondaRepository;
    
    @Autowired
    private SondaService sondaService;
    
    @Test
    @DisplayName("Cria uma sonda com sucesso sem enviar comando de movimento")
    public void criaSondaSemComando() {
        SondaDTO sondaDTO = mockSondaDTOSemComando();
        SondaEntity sondaEntity = mock(SondaEntity.class);
        when(sondaRepository.findByPlanetaAndAtualXAndAtualY(any(),any(),any())).thenReturn(Optional.empty());
        when(sondaRepository.saveAndFlush(sondaEntity)).thenReturn(sondaEntity);
        RetornoDTO retornoDTO = sondaService.criaSonda(sondaDTO);
        Assert.isTrue(retornoDTO.getAtualX().equals(1));
        Assert.isTrue(retornoDTO.getAtualY().equals(2));
        Assert.isTrue(retornoDTO.getDirecaoAtual().equals("N"));
    }
    
    @Test
    @DisplayName("Cria uma sonda com sucesso enviando comando de movimento")
    public void criaSondaComComando() {
        SondaDTO sondaDTO = mockSondaDTOComComando();
        SondaEntity sondaEntity = mock(SondaEntity.class);
        when(sondaRepository.findByPlanetaAndAtualXAndAtualY(any(),any(),any())).thenReturn(Optional.empty());
        when(sondaRepository.saveAndFlush(sondaEntity)).thenReturn(sondaEntity);
        RetornoDTO retornoDTO = sondaService.criaSonda(sondaDTO);
        Assert.isTrue(retornoDTO.getAtualX().equals(5));
        Assert.isTrue(retornoDTO.getAtualY().equals(1));
        Assert.isTrue(retornoDTO.getDirecaoAtual().equals("N"));
    }
    
    @Test
    @DisplayName("Tenta criar uma sonda em coordenada já ocupada")
    public void tentaCriarSondaCoordenadaOcupada() {
        SondaDTO sondaDTO = mockSondaDTOComComando();
        SondaEntity sondaEntity = mock(SondaEntity.class);
        when(sondaRepository.findByPlanetaAndAtualXAndAtualY(any(),any(),any())).thenReturn(Optional.of(sondaEntity));
        RetornoDTO retornoDTO = sondaService.criaSonda(sondaDTO);
        verify(sondaRepository, times(0)).saveAndFlush(sondaEntity);
        Assert.isTrue(retornoDTO.getErro().equals("As coordenadas x=3, y=3 já estão ocupadas por outra sonda. " +
                "Não é possível pousar neste ponto."));
    }
    
    @Test
    @DisplayName("Criar uma sonda e tenta mover para uma coordenada já ocupada")
    public void criaSondaTentaMoverCoordenadaOcupada() {
        SondaDTO sondaDTO = mockSondaDTOComComando();
        SondaEntity sondaEntity = mock(SondaEntity.class);
        when(sondaRepository.findByPlanetaAndAtualXAndAtualY(any(),any(),any())).thenReturn(Optional.empty(), Optional.of(sondaEntity));
        RetornoDTO retornoDTO = sondaService.criaSonda(sondaDTO);
        Assert.isTrue(retornoDTO.getAtualX().equals(3));
        Assert.isTrue(retornoDTO.getAtualY().equals(3));
        Assert.isTrue(retornoDTO.getErro().equals("As coordenadas x=4, y=3 já estão ocupadas por outra sonda. " +
                "Não é possível mover para este ponto. A sonda null ficou parada nas coordenadas x=3, y=3 apontando para Leste"));
    }
    
    @Test
    @DisplayName("Busca uma sonda por Id")
    public void buscaSondaPorId() {
        SondaEntity sondaEntity = mockSondaEntity();
        when(sondaRepository.findById(1L)).thenReturn(Optional.of(sondaEntity));
        RetornoDTO retornoDTO = sondaService.buscaSondaPorId(1L);
        Assert.notNull(retornoDTO);
        Assert.isTrue(retornoDTO.getSondaId().equals(1L));
        Assert.isTrue(retornoDTO.getAtualX().equals(0));
        Assert.isTrue(retornoDTO.getAtualY().equals(2));
        Assert.isTrue(retornoDTO.getDirecaoAtual().equals("W"));
    }
    
    @Test
    @DisplayName("Busca uma sonda por Id não existente")
    public void buscaSondaPorIdNaoExistente() {
        when(sondaRepository.findById(any())).thenReturn(Optional.empty());
        try {
            sondaService.buscaSondaPorId(2L);
        } catch (Exception ex) {
            Assert.isTrue(ex instanceof EntityNotFoundException);
            Assert.isTrue(ex.getMessage().equals("Sonda não encontrada"));
        }
    }
    
    @Test
    @DisplayName("Busca uma sonda por Id e movimenta através de comandos")
    public void movimentaSondaPorId() {
        SondaEntity sondaEntity = mockSondaEntity();
        ComandoDTO comandos = mockComandoDTO();
        when(sondaRepository.findByPlanetaAndAtualXAndAtualY(any(),any(),any())).thenReturn(Optional.empty());
        when(sondaRepository.saveAndFlush(sondaEntity)).thenReturn(sondaEntity);
        when(sondaRepository.findById(sondaEntity.getId())).thenReturn(Optional.of(sondaEntity));
        RetornoDTO retornoDTO = sondaService.movimentaSondaPorId(sondaEntity.getId(), comandos);
        Assert.notNull(retornoDTO);
        Assert.isTrue(retornoDTO.getSondaId().equals(sondaEntity.getId()));
        Assert.isTrue(retornoDTO.getAtualX().equals(1));
        Assert.isTrue(retornoDTO.getAtualY().equals(3));
        Assert.isTrue(retornoDTO.getDirecaoAtual().equals("N"));
        Assert.isTrue(retornoDTO.getCoordenadas().size() == comandos.getComandos().length());
    }
    
    @Test
    @DisplayName("Busca uma sonda por Id e tenta mover para uma coordenada já ocupada")
    public void buscaSondaTentaMoverCoordenadaOcupada() {
        SondaEntity sondaEntity1 = mockSondaEntity();
        SondaEntity sondaEntity2 = mock(SondaEntity.class);
        ComandoDTO comandos = mockComandoDTO();
        when(sondaRepository.findByPlanetaAndAtualXAndAtualY(any(),any(),any())).thenReturn(Optional.empty(), Optional.of(sondaEntity2));
        when(sondaRepository.saveAndFlush(sondaEntity1)).thenReturn(sondaEntity1);
        when(sondaRepository.findById(sondaEntity1.getId())).thenReturn(Optional.of(sondaEntity1));
        RetornoDTO retornoDTO = sondaService.movimentaSondaPorId(sondaEntity1.getId(), comandos);
        Assert.isTrue(retornoDTO.getAtualX().equals(0));
        Assert.isTrue(retornoDTO.getAtualY().equals(2));
        Assert.isTrue(retornoDTO.getErro().equals("As coordenadas x=0, y=1 já estão ocupadas por outra sonda. " +
                "Não é possível mover para este ponto. A sonda 1 ficou parada nas coordenadas x=0, y=2 apontando para Sul"));
    }
    
    @Test
    @DisplayName("Tenta mover uma sonda com Id não encontrado")
    public void movimentaSondaPorIdNaoExistente() {
        ComandoDTO comandos = mockComandoDTO();
        when(sondaRepository.findById(any())).thenReturn(Optional.empty());
        try {
            sondaService.movimentaSondaPorId(2L, comandos);
        } catch (Exception ex) {
            Assert.isTrue(ex instanceof EntityNotFoundException);
            Assert.isTrue(ex.getMessage().equals("Sonda não encontrada"));
        }
    }
    
    private SondaEntity mockSondaEntity() {
        SondaEntity sonda = new SondaEntity(1L, 1, 2, "N", 1, 2,
                "N", "Marte", null, new ArrayList<>());
        CoordenadaEntity coordenada = new CoordenadaEntity(1L, 1,2,"N",null, null, Timestamp.valueOf(LocalDateTime.now()));
        sonda.addCoordenada(coordenada);
        coordenada = new CoordenadaEntity(2L, 1,2,"W","L", null, Timestamp.valueOf(LocalDateTime.now()));
        sonda.addCoordenada(coordenada);
        coordenada = new CoordenadaEntity(3L, 0,2,"W","M", null, Timestamp.valueOf(LocalDateTime.now()));
        sonda.addCoordenada(coordenada);
        return sonda;
    }
    
    private SondaDTO mockSondaDTOSemComando() {
        SondaDTO sonda = new SondaDTO();
        sonda.setPlaneta("Marte");
        sonda.setDirecaoInical("N");
        sonda.setInicialX(1);
        sonda.setInicialY(2);
        return sonda;
    }
    
    private SondaDTO mockSondaDTOComComando() {
        SondaDTO sonda = new SondaDTO();
        sonda.setComandos("MMRMMRMRRML");
        sonda.setPlaneta("Marte");
        sonda.setDirecaoInical("E");
        sonda.setInicialX(3);
        sonda.setInicialY(3);
        return sonda;
    }
    
    private ComandoDTO mockComandoDTO(){
        ComandoDTO comando = new ComandoDTO();
        comando.setComandos("LMLMLMM");
        return comando;
    }
}
