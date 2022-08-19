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
public class CoordenadaEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private Integer x;
    
    @NotNull
    private Integer y;
    
    @Column(length = 1, nullable = false)
    private String direcao;
    
    @JsonBackReference
    @Setter
    @ManyToOne
    @JoinColumn(name="sonda_id", nullable=false)
    private SondaEntity sonda;
    
    public CoordenadaEntity(Integer x, Integer y, String direcao) {
        this.x = x;
        this.y = y;
        this.direcao = direcao;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CoordenadaEntity )) return false;
        return id != null && id.equals(((CoordenadaEntity) o).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return String.format("%d, %d, %s", this.x, this.y, this.direcao);
    }
}
