package br.com.thander.sonda.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RetornoDTO {
    
    private Long sondaId;
    private Integer inicialX;
    private Integer inicialY;
    private String direcaoInical;
    private Integer atualX;
    private Integer atualY;
    private String direcaoAtual;
    private String planeta;
    private String ultimoComando;
    private List<CoordenadaDTO> coordenadas;
    private String erro;
    
}
