package br.com.thander.sonda.model.entity;

import br.com.thander.sonda.model.dto.CoordenadaDTO;
import br.com.thander.sonda.model.dto.RetornoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@DisplayName("SondaEntityTest")
public class SondaEntityTest {
    
    private char COMANDO_MOVER;
    private char COMANDO_VIRAR_ESQUERDA;
    private char COMANDO_VIRAR_DIREITA;
    private String LESTE;
    private String OESTE;
    private String NORTE;
    private String SUL;
    
    @BeforeEach
    public void setUp() {
        COMANDO_MOVER = 'M';
        COMANDO_VIRAR_ESQUERDA = 'L';
        COMANDO_VIRAR_DIREITA = 'R';
        LESTE = "E";
        OESTE = "W";
        NORTE = "N";
        SUL = "S";
    }
    
    @Test
    @DisplayName("Testa coordenada atual da Sonda")
    public void coordenadaAtual() {
        SondaEntity sonda = mockSondaEntityVariasCoordenadas();
        CoordenadaEntity ultima = sonda.coordenadaAtual();
        
        Assert.isTrue(ultima.getX().equals(0));
        Assert.isTrue(ultima.getY().equals(2));
        Assert.isTrue(ultima.getDirecao().equals(OESTE));
        Assert.isTrue(ultima.getComando().equals(String.valueOf(COMANDO_MOVER)));
        Assert.isTrue(sonda.getAtualX().equals(0));
        Assert.isTrue(sonda.getAtualY().equals(2));
        Assert.isTrue(sonda.getDirecaoAtual().equals(OESTE));
    }
    
    @Test
    @DisplayName("Testa comandos aceitos")
    public void mover() {
        SondaEntity sonda = mockSondaEntity();
        CoordenadaEntity ultima = sonda.coordenadaAtual();
        
        Assert.isTrue(ultima.getX().equals(1));
        Assert.isTrue(ultima.getY().equals(2));
        Assert.isTrue(ultima.getDirecao().equals(NORTE));
        Assert.isNull(ultima.getComando());
        Assert.isTrue(sonda.getAtualX().equals(1));
        Assert.isTrue(sonda.getAtualY().equals(2));
        Assert.isTrue(sonda.getDirecaoAtual().equals(NORTE));
        
        sonda.mover(COMANDO_MOVER);
        ultima = sonda.coordenadaAtual();
        
        Assert.isTrue(ultima.getX().equals(1));
        Assert.isTrue(ultima.getY().equals(3));
        Assert.isTrue(ultima.getDirecao().equals(NORTE));
        Assert.isTrue(ultima.getComando().equals(String.valueOf(COMANDO_MOVER)));
        Assert.isTrue(sonda.getAtualX().equals(1));
        Assert.isTrue(sonda.getAtualY().equals(3));
        Assert.isTrue(sonda.getDirecaoAtual().equals(NORTE));
        
        sonda.mover(COMANDO_VIRAR_ESQUERDA);
        ultima = sonda.coordenadaAtual();
        
        Assert.isTrue(ultima.getX().equals(1));
        Assert.isTrue(ultima.getY().equals(3));
        Assert.isTrue(ultima.getDirecao().equals(OESTE));
        Assert.isTrue(ultima.getComando().equals(String.valueOf(COMANDO_VIRAR_ESQUERDA)));
        Assert.isTrue(sonda.getAtualX().equals(1));
        Assert.isTrue(sonda.getAtualY().equals(3));
        Assert.isTrue(sonda.getDirecaoAtual().equals(OESTE));
        
        sonda.mover(COMANDO_VIRAR_DIREITA);
        ultima = sonda.coordenadaAtual();
        
        Assert.isTrue(ultima.getX().equals(1));
        Assert.isTrue(ultima.getY().equals(3));
        Assert.isTrue(ultima.getDirecao().equals(NORTE));
        Assert.isTrue(ultima.getComando().equals(String.valueOf(COMANDO_VIRAR_DIREITA)));
        Assert.isTrue(sonda.getAtualX().equals(1));
        Assert.isTrue(sonda.getAtualY().equals(3));
        Assert.isTrue(sonda.getDirecaoAtual().equals(NORTE));
    }
    
    @Test
    @DisplayName("Converte para retorno de uma sonda pousou e movimentou com sucesso")
    public void converteParaRetornoSucesso() {
        SondaEntity sonda = mockSondaEntity();
        sonda.mover(COMANDO_MOVER);
        sonda.mover(COMANDO_VIRAR_DIREITA);
        sonda.mover(COMANDO_MOVER);
        
        RetornoDTO retornoDTO = sonda.converteParaRetorno();
        
        Assert.isTrue(sonda.getInicialX().equals(retornoDTO.getInicialX()));
        Assert.isTrue(sonda.getInicialY().equals(retornoDTO.getInicialY()));
        Assert.isTrue(sonda.getDirecaoInical().equals(retornoDTO.getDirecaoInical()));
        Assert.isTrue(sonda.getAtualX().equals(retornoDTO.getAtualX()));
        Assert.isTrue(sonda.getAtualY().equals(retornoDTO.getAtualY()));
        Assert.isTrue(sonda.getDirecaoAtual().equals(retornoDTO.getDirecaoAtual()));
        Assert.isTrue(sonda.getPlaneta().equals(retornoDTO.getPlaneta()));
        
        for (int i = 0; i < retornoDTO.getCoordenadas().size(); i++) {
            CoordenadaDTO coordenadaDTO = retornoDTO.getCoordenadas().get(i);
            
            Assert.isTrue(sonda.getCoordenadas().get(i).getX().equals(coordenadaDTO.getX()));
            Assert.isTrue(sonda.getCoordenadas().get(i).getY().equals(coordenadaDTO.getY()));
            Assert.isTrue(sonda.getCoordenadas().get(i).getDirecao().equals(coordenadaDTO.getDirecao()));
            if (i == 0)
                Assert.isNull(coordenadaDTO.getComandoExecutado());
            else
                Assert.isTrue(sonda.getCoordenadas().get(i).getComando().equals(coordenadaDTO.getComandoExecutado()));
        }
        
        Assert.isNull(retornoDTO.getErro());
    }
    
    @Test
    @DisplayName("Converte para retorno de uma sonda que pousou e tentou se mover para coordenada ja ocupada")
    public void converteParaRetornoComColisaoMovimento() {
        SondaEntity sonda = mockSondaEntity();
        sonda.mover(COMANDO_MOVER);
        
        RetornoDTO retornoDTO = sonda.converteParaRetorno("Já existe sonda nessas coordenadas");
        
        Assert.isTrue(sonda.getInicialX().equals(retornoDTO.getInicialX()));
        Assert.isTrue(sonda.getInicialY().equals(retornoDTO.getInicialY()));
        Assert.isTrue(sonda.getDirecaoInical().equals(retornoDTO.getDirecaoInical()));
        Assert.isTrue(sonda.getAtualX().equals(retornoDTO.getAtualX()));
        Assert.isTrue(sonda.getAtualY().equals(retornoDTO.getAtualY()));
        Assert.isTrue(sonda.getDirecaoAtual().equals(retornoDTO.getDirecaoAtual()));
        Assert.isTrue(sonda.getPlaneta().equals(retornoDTO.getPlaneta()));
        
        for (int i = 0; i < sonda.getCoordenadas().size(); i++) {
            CoordenadaDTO coordenadaDTO = sonda.getCoordenadas().get(i).converteParaCoordenadaDTO();
            
            Assert.isTrue(sonda.getCoordenadas().get(i).getX().equals(coordenadaDTO.getX()));
            Assert.isTrue(sonda.getCoordenadas().get(i).getY().equals(coordenadaDTO.getY()));
            Assert.isTrue(sonda.getCoordenadas().get(i).getDirecao().equals(coordenadaDTO.getDirecao()));
            if (i == 0)
                Assert.isNull(coordenadaDTO.getComandoExecutado());
            else
                Assert.isTrue(sonda.getCoordenadas().get(i).getComando().equals(coordenadaDTO.getComandoExecutado()));
        }
        
        Assert.isTrue(retornoDTO.getErro().equals("Já existe sonda nessas coordenadas"));
    }
    
    @Test
    @DisplayName("Converte para retorno de uma sonda existente que movimentou com sucesso")
    public void ConverteParaRetornoSucessoAlteracao() {
        SondaEntity sonda = mockSondaEntityVariasCoordenadas();
        int index = sonda.getCoordenadas().indexOf(sonda.coordenadaAtual());
        sonda.mover(COMANDO_MOVER);
        sonda.mover(COMANDO_MOVER);
    
        RetornoDTO retornoDTO = sonda.converteParaRetornoPut(index);
    
        Assert.isTrue(sonda.getCoordenadas().get(index).getX().equals(retornoDTO.getInicialX()));
        Assert.isTrue(sonda.getCoordenadas().get(index).getY().equals(retornoDTO.getInicialY()));
        Assert.isTrue(sonda.getCoordenadas().get(index).getDirecao().equals(retornoDTO.getDirecaoInical()));
        Assert.isTrue(sonda.getAtualX().equals(retornoDTO.getAtualX()));
        Assert.isTrue(sonda.getAtualY().equals(retornoDTO.getAtualY()));
        Assert.isTrue(sonda.getDirecaoAtual().equals(retornoDTO.getDirecaoAtual()));
        Assert.isTrue(sonda.getPlaneta().equals(retornoDTO.getPlaneta()));
    
        for (int i = 1; i < retornoDTO.getCoordenadas().size(); i++) {
            CoordenadaDTO coordenadaDTO = retornoDTO.getCoordenadas().get(i-1);
        
            Assert.isTrue(sonda.getCoordenadas().get(index + i).getX().equals(coordenadaDTO.getX()));
            Assert.isTrue(sonda.getCoordenadas().get(index + i).getY().equals(coordenadaDTO.getY()));
            Assert.isTrue(sonda.getCoordenadas().get(index + i).getDirecao().equals(coordenadaDTO.getDirecao()));
            Assert.isTrue(sonda.getCoordenadas().get(index + i).getComando().equals(coordenadaDTO.getComandoExecutado()));
        }
    
        Assert.isTrue(sonda.getCoordenadas().size() == 5);
        Assert.isTrue(retornoDTO.getCoordenadas().size() == 2);
        Assert.isNull(retornoDTO.getErro());
    }
    
    @Test
    @DisplayName("Converte para retorno de uma sonda existente que tentou se mover para coordenada ja ocupada")
    public void converteParaRetornoComColisaoMovimentoAlteracao() {
        SondaEntity sonda = mockSondaEntityVariasCoordenadas();
        int index = sonda.getCoordenadas().indexOf(sonda.coordenadaAtual());
        sonda.mover(COMANDO_MOVER);
        sonda.mover(COMANDO_MOVER);
        
        RetornoDTO retornoDTO = sonda.converteParaRetornoPut(index, "Já existe sonda nessas coordenadas");
        
        Assert.isTrue(sonda.getCoordenadas().get(index).getX().equals(retornoDTO.getInicialX()));
        Assert.isTrue(sonda.getCoordenadas().get(index).getY().equals(retornoDTO.getInicialY()));
        Assert.isTrue(sonda.getCoordenadas().get(index).getDirecao().equals(retornoDTO.getDirecaoInical()));
        Assert.isTrue(sonda.getAtualX().equals(retornoDTO.getAtualX()));
        Assert.isTrue(sonda.getAtualY().equals(retornoDTO.getAtualY()));
        Assert.isTrue(sonda.getDirecaoAtual().equals(retornoDTO.getDirecaoAtual()));
        Assert.isTrue(sonda.getPlaneta().equals(retornoDTO.getPlaneta()));
        
        for (int i = 1; i < retornoDTO.getCoordenadas().size(); i++) {
            CoordenadaDTO coordenadaDTO = retornoDTO.getCoordenadas().get(i-1);
            
            Assert.isTrue(sonda.getCoordenadas().get(index + i).getX().equals(coordenadaDTO.getX()));
            Assert.isTrue(sonda.getCoordenadas().get(index + i).getY().equals(coordenadaDTO.getY()));
            Assert.isTrue(sonda.getCoordenadas().get(index + i).getDirecao().equals(coordenadaDTO.getDirecao()));
            Assert.isTrue(sonda.getCoordenadas().get(index + i).getComando().equals(coordenadaDTO.getComandoExecutado()));
        }
    
        Assert.isTrue(retornoDTO.getErro().equals("Já existe sonda nessas coordenadas"));
    }
    
    private SondaEntity mockSondaEntity() {
        return new SondaEntity(1, 2, "N", "Marte", null);
    }
    
    private SondaEntity mockSondaEntityVariasCoordenadas() {
        SondaEntity sonda = new SondaEntity(1L, 1, 2, "N", 1, 2,
                "N", "Marte", null, new ArrayList<>());
        CoordenadaEntity coordenada = new CoordenadaEntity(1L, 1, 2, "N", null, null, Timestamp.valueOf(LocalDateTime.now()));
        sonda.addCoordenada(coordenada);
        coordenada = new CoordenadaEntity(2L, 1, 2, "W", "L", null, Timestamp.valueOf(LocalDateTime.now()));
        sonda.addCoordenada(coordenada);
        coordenada = new CoordenadaEntity(3L, 0, 2, "W", "M", null, Timestamp.valueOf(LocalDateTime.now()));
        sonda.addCoordenada(coordenada);
        return sonda;
    }
}
