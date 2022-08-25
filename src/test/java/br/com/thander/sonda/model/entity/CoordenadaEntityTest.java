package br.com.thander.sonda.model.entity;

import br.com.thander.sonda.model.dto.CoordenadaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;

@DisplayName("CoordenadaEntityTest")
public class CoordenadaEntityTest {
    
    private char comandoMover;
    private char comandoVirarEsquerda;
    private char comandoVirarDireita;
    private char comandoInvalido;
    private CoordenadaEntity coordenada;
    private CoordenadaDTO coordenadaDTO;
    
    @BeforeEach
    public void setUp() {
        comandoMover = 'M';
        comandoVirarEsquerda = 'L';
        comandoVirarDireita = 'R';
        comandoInvalido = 'A';
        coordenada = new CoordenadaEntity(1, 2, "N", null);
        coordenadaDTO = new CoordenadaDTO(1, 2, "N", null);
    }
    
    @Test
    @DisplayName("Calcula proxima coordenada com comando Virar a Esquerda")
    public void calculaProximaCoordenadaVirarEsquerda() {
        CoordenadaEntity proxCoordenada = coordenada.calculaProximaCoordenada(comandoVirarEsquerda);
        Assert.isTrue(proxCoordenada.getX().equals(1));
        Assert.isTrue(proxCoordenada.getY().equals(2));
        Assert.isTrue(proxCoordenada.getDirecao().equals("W"));
        Assert.isTrue(proxCoordenada.getComando().equals(String.valueOf(comandoVirarEsquerda)));
        
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoVirarEsquerda);
        Assert.isTrue(proxCoordenada.getX().equals(1));
        Assert.isTrue(proxCoordenada.getY().equals(2));
        Assert.isTrue(proxCoordenada.getDirecao().equals("S"));
        Assert.isTrue(proxCoordenada.getComando().equals(String.valueOf(comandoVirarEsquerda)));

        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoVirarEsquerda);
        Assert.isTrue(proxCoordenada.getX().equals(1));
        Assert.isTrue(proxCoordenada.getY().equals(2));
        Assert.isTrue(proxCoordenada.getDirecao().equals("E"));
        Assert.isTrue(proxCoordenada.getComando().equals(String.valueOf(comandoVirarEsquerda)));

        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoVirarEsquerda);
        Assert.isTrue(proxCoordenada.getX().equals(1));
        Assert.isTrue(proxCoordenada.getY().equals(2));
        Assert.isTrue(proxCoordenada.getDirecao().equals("N"));
        Assert.isTrue(proxCoordenada.getComando().equals(String.valueOf(comandoVirarEsquerda)));
    }
    
    @Test
    @DisplayName("Calcula proxima coordenada com comando Virar a Direita")
    public void calculaProximaCoordenadaVirarDireita() {
        CoordenadaEntity proxCoordenada = coordenada.calculaProximaCoordenada(comandoVirarDireita);
        Assert.isTrue(proxCoordenada.getX().equals(1));
        Assert.isTrue(proxCoordenada.getY().equals(2));
        Assert.isTrue(proxCoordenada.getDirecao().equals("E"));
        Assert.isTrue(proxCoordenada.getComando().equals(String.valueOf(comandoVirarDireita)));
        
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoVirarDireita);
        Assert.isTrue(proxCoordenada.getX().equals(1));
        Assert.isTrue(proxCoordenada.getY().equals(2));
        Assert.isTrue(proxCoordenada.getDirecao().equals("S"));
        Assert.isTrue(proxCoordenada.getComando().equals(String.valueOf(comandoVirarDireita)));
        
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoVirarDireita);
        Assert.isTrue(proxCoordenada.getX().equals(1));
        Assert.isTrue(proxCoordenada.getY().equals(2));
        Assert.isTrue(proxCoordenada.getDirecao().equals("W"));
        Assert.isTrue(proxCoordenada.getComando().equals(String.valueOf(comandoVirarDireita)));
        
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoVirarDireita);
        Assert.isTrue(proxCoordenada.getX().equals(1));
        Assert.isTrue(proxCoordenada.getY().equals(2));
        Assert.isTrue(proxCoordenada.getDirecao().equals("N"));
        Assert.isTrue(proxCoordenada.getComando().equals(String.valueOf(comandoVirarDireita)));
    }
    
    @Test
    @DisplayName("Calcula proxima coordenada com comando Virar a Esquerda e Mover")
    public void calculaProximaCoordenadaVirarEsquerdaEMover() {
        CoordenadaEntity proxCoordenada = coordenada.calculaProximaCoordenada(comandoVirarEsquerda);
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoMover);
        Assert.isTrue(proxCoordenada.getX().equals(0));
        Assert.isTrue(proxCoordenada.getY().equals(2));
        Assert.isTrue(proxCoordenada.getDirecao().equals("W"));
        Assert.isTrue(proxCoordenada.getComando().equals(String.valueOf(comandoMover)));
    
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoVirarEsquerda);
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoMover);
        Assert.isTrue(proxCoordenada.getX().equals(0));
        Assert.isTrue(proxCoordenada.getY().equals(1));
        Assert.isTrue(proxCoordenada.getDirecao().equals("S"));
        Assert.isTrue(proxCoordenada.getComando().equals(String.valueOf(comandoMover)));
    
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoVirarEsquerda);
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoMover);
        Assert.isTrue(proxCoordenada.getX().equals(1));
        Assert.isTrue(proxCoordenada.getY().equals(1));
        Assert.isTrue(proxCoordenada.getDirecao().equals("E"));
        Assert.isTrue(proxCoordenada.getComando().equals(String.valueOf(comandoMover)));
    
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoVirarEsquerda);
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoMover);
        Assert.isTrue(proxCoordenada.getX().equals(1));
        Assert.isTrue(proxCoordenada.getY().equals(2));
        Assert.isTrue(proxCoordenada.getDirecao().equals("N"));
        Assert.isTrue(proxCoordenada.getComando().equals(String.valueOf(comandoMover)));
    }
    
    @Test
    @DisplayName("Calcula proxima coordenada com comando Virar a Direita e Mover")
    public void calculaProximaCoordenadaVirarDireitaEMover() {
        CoordenadaEntity proxCoordenada = coordenada.calculaProximaCoordenada(comandoVirarDireita);
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoMover);
        Assert.isTrue(proxCoordenada.getX().equals(2));
        Assert.isTrue(proxCoordenada.getY().equals(2));
        Assert.isTrue(proxCoordenada.getDirecao().equals("E"));
        Assert.isTrue(proxCoordenada.getComando().equals(String.valueOf(comandoMover)));
        
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoVirarDireita);
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoMover);
        Assert.isTrue(proxCoordenada.getX().equals(2));
        Assert.isTrue(proxCoordenada.getY().equals(1));
        Assert.isTrue(proxCoordenada.getDirecao().equals("S"));
        Assert.isTrue(proxCoordenada.getComando().equals(String.valueOf(comandoMover)));
        
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoVirarDireita);
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoMover);
        Assert.isTrue(proxCoordenada.getX().equals(1));
        Assert.isTrue(proxCoordenada.getY().equals(1));
        Assert.isTrue(proxCoordenada.getDirecao().equals("W"));
        Assert.isTrue(proxCoordenada.getComando().equals(String.valueOf(comandoMover)));
        
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoVirarDireita);
        proxCoordenada = proxCoordenada.calculaProximaCoordenada(comandoMover);
        Assert.isTrue(proxCoordenada.getX().equals(1));
        Assert.isTrue(proxCoordenada.getY().equals(2));
        Assert.isTrue(proxCoordenada.getDirecao().equals("N"));
        Assert.isTrue(proxCoordenada.getComando().equals(String.valueOf(comandoMover)));
    }
    
    @Test
    @DisplayName("Calcula proxima coordenada com comando invalido")
    public void calculaProximaCoordenadaComandoInvalido() {
        CoordenadaEntity proxCoordenada = coordenada.calculaProximaCoordenada(comandoInvalido);
        Assert.isTrue(proxCoordenada.getX().equals(1));
        Assert.isTrue(proxCoordenada.getY().equals(2));
        Assert.isTrue(proxCoordenada.getDirecao().equals("N"));
        Assert.isNull(proxCoordenada.getComando());
    }
    
    @Test
    @DisplayName("Testa toString() que traduz coordenadas")
    public void calculaProximaCoordenadaToString() {
        Assert.isTrue(coordenada.toString().equals("x=1, y=2 apontando para Norte"));
        
        coordenada = coordenada.calculaProximaCoordenada(comandoVirarEsquerda);
        Assert.isTrue(coordenada.toString().equals("x=1, y=2 apontando para Oeste"));
    
        coordenada = coordenada.calculaProximaCoordenada(comandoVirarEsquerda);
        Assert.isTrue(coordenada.toString().equals("x=1, y=2 apontando para Sul"));
    
        coordenada = coordenada.calculaProximaCoordenada(comandoVirarEsquerda);
        Assert.isTrue(coordenada.toString().equals("x=1, y=2 apontando para Leste"));
    
        coordenada = coordenada.calculaProximaCoordenada(comandoInvalido);
        Assert.isTrue(coordenada.toString().equals("x=1, y=2 apontando para Leste"));
    }
    
    @Test
    @DisplayName("Testa conversao para CoordenadaDTO")
    public void converteParaCoordenadaDTO() {
        CoordenadaEntity proxCoordenada = coordenada.calculaProximaCoordenada(comandoVirarEsquerda);
        CoordenadaDTO coordenadaDTO = proxCoordenada.converteParaCoordenadaDTO();
        Assert.isTrue(proxCoordenada.getX().equals(coordenadaDTO.getX()));
        Assert.isTrue(proxCoordenada.getY().equals(coordenadaDTO.getY()));
        Assert.isTrue(proxCoordenada.getDirecao().equals(coordenadaDTO.getDirecao()));
        Assert.isTrue(proxCoordenada.getComando().equals(coordenadaDTO.getComandoExecutado()));
    }
}
