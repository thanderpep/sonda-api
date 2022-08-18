package br.com.thander.sonda.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Entity
@Table(name = "sonda")
@NoArgsConstructor
public class SondaEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "planeta", nullable = false)
    private String planeta;
    
    @Column(name = "inicial_x", nullable = false)
    private Integer inicialX;
    
    @Column(name = "inicial_y", nullable = false)
    private Integer inicialY;
    
    @Column(name = "direcao_inicial", length = 1, nullable = false)
    private String direcaoInical;
    
    @Column(name = "final_x")
    private Integer finalX;

    @Column(name = "final_y")
    private Integer finalY;

    @Column(name = "direcao_final", length = 1)
    private String direcaoFinal;
    
    @Column(name = "comandos", nullable = false)
    private String comandos;
    
    public SondaEntity(String planeta, Integer inicialX, Integer inicialY,
                       String direcaoInical, String comandos) {
        this.planeta = planeta;
        this.inicialX = inicialX;
        this.inicialY = inicialY;
        this.direcaoInical = direcaoInical;
        this.comandos = comandos;
    }
}
