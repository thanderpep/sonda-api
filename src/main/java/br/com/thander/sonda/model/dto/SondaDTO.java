package br.com.thander.sonda.model.dto;

import br.com.thander.sonda.model.entity.SondaEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SondaDTO {
    
    @NotNull(message = "Posição inicial X deve ser informada")
    @Min(value = 0, message = "Posição inicial X deve ser entre 0 e 5")
    @Max(value = 5, message = "Posição inicial X deve ser entre 0 e 5")
    private Integer inicialX;
    
    @NotNull(message = "Posição inicial Y deve ser informada")
    @Min(value = 0, message = "Posição inicial Y deve ser entre 0 e 5")
    @Max(value = 5, message = "Posição inicial Y deve ser entre 0 e 5")
    private Integer inicialY;
    
    @NotBlank(message = "Direção inicial deve ser informada")
    @Pattern(regexp = "[ewnsEWNS]", message = "Valores aceitos para a direção inicial: E -> Leste, W -> Oeste, N -> Norte, S -> Sul")
    private String direcaoInical;
    
    @NotBlank(message = "Planeta deve ser informado")
    private String planeta;
    
    @Size(max = 255, message = "Sequência de comandos deve conter um máximo de 255 caracteres")
    @Pattern(regexp = "[mrlMLR]*", message = "Valores aceitos para um comando: M -> Andar para a frente na direção que está 1 posição, " +
            "L -> Virar a sonda para a esquerda (90 graus), R -> Virar a sonda para a direita (90 graus)")
    private String comandos;
    
    public SondaEntity converteParaSondaEntity() {
        return new SondaEntity(this.inicialX, this.inicialY, this.direcaoInical, this.planeta, this.comandos);
    }
}
