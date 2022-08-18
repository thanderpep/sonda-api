package br.com.thander.sonda.model.dto;

import br.com.thander.sonda.model.entity.SondaEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SondaDTO {
    
    @NotBlank(message = "Planeta deve ser informado")
    private String planeta;
    
    @NotNull(message = "Posição inicial X deve ser informada")
    private Integer inicialX;
    
    @NotNull(message = "Posição inicial Y deve ser informada")
    private Integer inicialY;
    
    @NotBlank(message = "Direção inicial deve ser informada")
    @Pattern(regexp = "[ewnsEWNS]", message = "Valores aceitos para a direção inicial: E -> Leste, W -> Oeste, N -> Norte, S -> Sul")
    private String direcaoInical;
    
    @NotBlank(message = "Sequência de comandos deve ser informada")
    @Pattern(regexp = "[mrlMLR]*", message = "Valores aceitos para um comando: M -> Andar para a frente na direção que está 1 posição, " +
            "L -> Virar a sonda para a esquerda (90 graus), R -> Virar a sonda para a direita (90 graus)")
    private String comandos;
    
    public SondaEntity transformaParaObjeto() {
        return new SondaEntity(this.planeta.toUpperCase(), this.inicialX, this.inicialY,
                this.direcaoInical.toUpperCase(), this.comandos.toUpperCase());
    }
}
