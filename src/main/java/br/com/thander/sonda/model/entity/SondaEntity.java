package br.com.thander.sonda.model.entity;

import br.com.thander.sonda.model.dto.RetornoSondaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "sonda")
@NoArgsConstructor
public class SondaEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "inicial_x", nullable = false)
    private Integer inicialX;
    
    @Column(name = "inicial_y", nullable = false)
    private Integer inicialY;
    
    @Column(name = "direcao_inicial", length = 1, nullable = false)
    private String direcaoInical;
    
    @Column(name = "atual_x")
    private Integer atualX;
    
    @Column(name = "atual_y")
    private Integer atualY;
    
    @Column(name = "direcao_atual", length = 1, nullable = false)
    private String direcaoAtual;
    
    @Column(name = "planeta", nullable = false)
    private String planeta;
    
    @OneToMany(mappedBy = "sonda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoordenadaEntity> coordenadas = new ArrayList<>();
    
    public SondaEntity(Integer inicialX, Integer inicialY, String direcaoInical, String planeta) {
        this.inicialX = inicialX;
        this.inicialY = inicialY;
        this.direcaoInical = direcaoInical.toUpperCase();
        this.atualX = inicialX;
        this.atualY = inicialY;
        this.direcaoAtual = direcaoInical.toUpperCase();
        this.planeta = planeta.toUpperCase();
        this.addHistoricoCoordenada(new CoordenadaEntity(inicialX, inicialY, direcaoInical.toUpperCase(), null));
    }
    
    public CoordenadaEntity coordenadaAtual() {
        if (this.coordenadas != null && !this.coordenadas.isEmpty()) {
            return this.coordenadas.get(this.coordenadas.size()-1);
        }
        return null;
    }
    
    private void addHistoricoCoordenada(CoordenadaEntity coordenada) {
        this.coordenadas.add(coordenada);
        coordenada.setSonda(this);
    }
    
    public void mover(char comando) {
        CoordenadaEntity novaCoordenada = coordenadaAtual().calculaProximaCoordenada(comando);
        this.addHistoricoCoordenada(novaCoordenada);
        this.atualX = novaCoordenada.getX();
        this.atualY = novaCoordenada.getY();
        this.direcaoAtual = novaCoordenada.getDirecao();
    }
    
    public RetornoSondaDTO converteParaRetornoColisaoPouso(String erro){
        return new RetornoSondaDTO(this.inicialX, this.inicialY, this.direcaoInical, this.planeta, erro);
    }
    
    public RetornoSondaDTO converteParaRetornoColisaoMovimento(String erro){
        return new RetornoSondaDTO(this.id, this.inicialX, this.inicialY, this.direcaoInical,
                this.atualX, this.atualY, this.direcaoAtual, this.planeta, erro);
    }
    
    public RetornoSondaDTO converteParaRetornoSondaSucesso(){
        return new RetornoSondaDTO(this.id, this.inicialX, this.inicialY, this.direcaoInical,
                this.atualX, this.atualY, this.direcaoAtual, this.planeta);
    }
}
