package br.com.thander.sonda.model.entity;

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
    
    @Column(name = "planeta", nullable = false)
    private String planeta;
    
    @Column(name = "tamanho", nullable = false)
    private Integer tamanho;
    
    @OneToMany(mappedBy = "sonda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoCoordenadaEntity> coordenadas = new ArrayList<>();
    
    public SondaEntity(Integer inicialX, Integer inicialY, String direcaoInical,
                       String planeta, Integer tamanho) {
        this.inicialX = inicialX;
        this.inicialY = inicialY;
        this.direcaoInical = direcaoInical.toUpperCase();
        this.planeta = planeta;
        this.tamanho = tamanho;
    }
    
    private void addHistoricoCoordenada(HistoricoCoordenadaEntity coordenada) {
        this.coordenadas.add(coordenada);
        coordenada.setSonda(this);
    }
    
    public HistoricoCoordenadaEntity capturaCoordenadaAtual() {
        if (this.coordenadas != null && !this.coordenadas.isEmpty())
            return this.coordenadas.get(this.coordenadas.size()-1);
        else
            return new HistoricoCoordenadaEntity(this.inicialX, this.inicialY, this.direcaoInical, "");
    }
    
    public void movimentaSonda(char comando) {
        HistoricoCoordenadaEntity coordenadaAtual = capturaCoordenadaAtual();
        switch (comando) {
            case 'M':
                andarParaFrente(coordenadaAtual, comando);
                break;
            case 'L':
                giraParaEsquerda(coordenadaAtual, comando);
                break;
            case 'R':
                giraParaDireita(coordenadaAtual, comando);
                break;
            default:
                break;
        }
    }

    private void andarParaFrente(HistoricoCoordenadaEntity coordenadaAtual, char comando) {
        Integer novoX = coordenadaAtual.getX();
        Integer novoY = coordenadaAtual.getY();
        
        switch (coordenadaAtual.getDirecao()) {
            case "E":
                if (coordenadaAtual.getX() < this.tamanho)
                    novoX++;
                break;
            case "S":
                if (coordenadaAtual.getY() > 1)
                    novoY--;
                break;
            case "W":
                if (coordenadaAtual.getX() > 1)
                    novoX--;
                break;
            case "N":
                if (coordenadaAtual.getY() < this.tamanho)
                    novoY++;
                break;
            default:
                break;
        }
    
        this.addHistoricoCoordenada(new HistoricoCoordenadaEntity(novoX, novoY,
                coordenadaAtual.getDirecao(), String.valueOf(comando)));
    }

    private void giraParaEsquerda(HistoricoCoordenadaEntity coordenadaAtual, char comando) {
        String novaDirecao = coordenadaAtual.getDirecao();
        
        switch (coordenadaAtual.getDirecao()) {
            case "E":
                novaDirecao = "N";
                break;
            case "N":
                novaDirecao = "W";
                break;
            case "W":
                novaDirecao = "S";
                break;
            case "S":
                novaDirecao = "E";
                break;
            default:
                break;
        }
    
        this.addHistoricoCoordenada(new HistoricoCoordenadaEntity(coordenadaAtual.getX(), coordenadaAtual.getY(),
                novaDirecao, String.valueOf(comando)));
    }

    private void giraParaDireita(HistoricoCoordenadaEntity coordenadaAtual, char comando) {
        String novaDirecao = coordenadaAtual.getDirecao();
        
        switch (coordenadaAtual.getDirecao()) {
            case "E":
                novaDirecao =  "S";
                break;
            case "S":
                novaDirecao =  "W";
                break;
            case "W":
                novaDirecao =  "N";
                break;
            case "N":
                novaDirecao =  "E";
            default:
                break;
        }
    
        this.addHistoricoCoordenada(new HistoricoCoordenadaEntity(coordenadaAtual.getX(), coordenadaAtual.getY(),
                novaDirecao, String.valueOf(comando)));
    }
}
