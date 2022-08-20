package br.com.thander.sonda.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Entity
@Table(name = "coordenada")
@NoArgsConstructor
public class HistoricoCoordenadaEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private Integer x;
    
    @NotNull
    private Integer y;
    
    @Column(length = 1, nullable = false)
    private String direcao;
    
    @Column(length = 1, nullable = false)
    private String comando;
    
    @JsonBackReference
    @Setter
    @ManyToOne
    @JoinColumn(name="sonda_id", nullable=false)
    private SondaEntity sonda;
    
    public HistoricoCoordenadaEntity(Integer x, Integer y, String direcao, String comando) {
        this.x = x;
        this.y = y;
        this.direcao = direcao;
        this.comando = comando;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoricoCoordenadaEntity)) return false;
        return id != null && id.equals(((HistoricoCoordenadaEntity) o).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return String.format("x=%d, y=%d apontando para %s", this.x, this.y, traduzDirecao(this.direcao));
    }
    
    private String traduzDirecao(String direcao) {
        switch (direcao) {
            case "E":
                return "Leste";
            case "N":
                return "Norte";
            case "W":
                return "Oeste";
            case "S":
                return "Sul";
            default:
                return "posição inválida";
        }
    }
}
