package br.com.thander.sonda.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    
//    /***
//     * Construtor usado para casos de pouso de sonda (POST)
//     * @param inicialX
//     * @param inicialY
//     * @param direcaoInical
//     * @param planeta
//     * @param comandos
//     * @param erro
//     */
//    public RetornoDTO(Integer inicialX, Integer inicialY, String direcaoInical, String planeta, String comandos, String erro){
//        this.inicialX = inicialX;
//        this.inicialY = inicialY;
//        this.direcaoInical = direcaoInical.toUpperCase();
//        this.planeta = planeta.toUpperCase();
//        this.ultimoComando = comandos;
//        this.erro = erro;
//    }
//
//    /***
//     * Construtor usado para casos em que se movimenta  para uma posição já ocupada
//     * @param sondaId
//     * @param inicialX
//     * @param inicialY
//     * @param direcaoInical
//     * @param atualX
//     * @param atualY
//     * @param direcaoAtual
//     * @param planeta
//     * @param comandos
//     * @param coordenadas
//     * @param erro
//     */
//    public RetornoDTO(Long sondaId, Integer inicialX, Integer inicialY, String direcaoInical,
//                      Integer atualX, Integer atualY, String direcaoAtual, String planeta,
//                      String comandos, List<CoordenadaDTO> coordenadas, String erro){
//        this(inicialX, inicialY, direcaoInical, planeta, comandos, erro);
//        this.sondaId = sondaId;
//        this.atualX = atualX;
//        this.atualY = atualY;
//        this.direcaoAtual = direcaoAtual.toUpperCase();
//        this.coordenadas = coordenadas;
//    }
}
