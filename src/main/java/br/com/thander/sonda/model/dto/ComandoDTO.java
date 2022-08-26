package br.com.thander.sonda.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ComandoDTO {
    
    @NotBlank(message = "Ao menos um comando deve ser informado")
    @Size(max = 255, message = "Sequência de comandos deve conter um máximo de 255 caracteres")
    @Pattern(regexp = "[MLR]*", message = "Valores aceitos para um comando: M -> Andar para a frente na direção que está 1 posição, " +
            "L -> Virar a sonda para a esquerda (90 graus), R -> Virar a sonda para a direita (90 graus)")
    private String comandos;
}
