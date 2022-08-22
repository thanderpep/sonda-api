package br.com.thander.sonda.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RetornoSondaDTO {
    private Long sondaId;
    private Integer inicialX;
    private Integer inicialY;
    private String direcaoInical;
    private Integer atualX;
    private Integer atualY;
    private String direcaoAtual;
    private String planeta;
    private String erro;
    @Setter
    private String comandos;
    
    /***
     * Construtor usado para casos em que a sonda não pode pousar em uma posição já ocupada
     * @param inicialX
     * @param inicialY
     * @param direcaoInical
     * @param planeta
     * @param erro
     */
    public RetornoSondaDTO(Integer inicialX, Integer inicialY, String direcaoInical, String planeta, String erro){
        this.inicialX = inicialX;
        this.inicialY = inicialY;
        this.direcaoInical = direcaoInical.toUpperCase();
        this.planeta = planeta.toUpperCase();
        this.erro = erro;
    }
    
    /***
     * Construtor usado para casos em que a sonda não pode movimentar para uma posição já ocupada
     * @param sondaId
     * @param inicialX
     * @param inicialY
     * @param direcaoInical
     * @param atualX
     * @param atualY
     * @param direcaoAtual
     * @param planeta
     * @param erro
     */
    public RetornoSondaDTO(Long sondaId, Integer inicialX, Integer inicialY, String direcaoInical,
                           Integer atualX, Integer atualY, String direcaoAtual, String planeta, String erro){
        this(inicialX, inicialY, direcaoInical, planeta, erro);
        this.sondaId = sondaId;
        this.atualX = atualX;
        this.atualY = atualY;
        this.direcaoAtual = direcaoAtual.toUpperCase();
    }
    
    /***
     * Construtor usado para casos em que a sonda executa todos os comandos com sucesso
     * @param sondaId
     * @param inicialX
     * @param inicialY
     * @param direcaoInical
     * @param atualX
     * @param atualY
     * @param direcaoAtual
     * @param planeta
     */
    public RetornoSondaDTO(Long sondaId, Integer inicialX, Integer inicialY, String direcaoInical,
                           Integer atualX, Integer atualY, String direcaoAtual, String planeta){
        this(sondaId, inicialX, inicialY, direcaoInical, atualX, atualY, direcaoAtual, planeta, null);
    }
}