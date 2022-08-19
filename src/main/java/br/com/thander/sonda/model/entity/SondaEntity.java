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
    
    @Column(name = "planeta", nullable = false)
    private String planeta;
    
    @Column(name = "tamanho", nullable = false)
    private Integer tamanho;
    
    @Column(name = "comandos", nullable = false)
    private String comandos;
    
    @OneToMany(mappedBy = "sonda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoordenadaEntity> coordenadas = new ArrayList<>();
    
    public SondaEntity(String planeta, Integer tamanho, String comandos) {
        this.planeta = planeta;
        this.tamanho = tamanho;
        this.comandos = comandos;
    }
    
    public void addCoordenada(CoordenadaEntity coordenada) {
        this.coordenadas.add(coordenada);
        coordenada.setSonda(this);
    }
    
    public CoordenadaEntity ultimaCoordenada() {
        if (this.coordenadas != null && !this.coordenadas.isEmpty()) {
            return this.coordenadas.get(this.coordenadas.size()-1);
        }
        return null;
    }
    
    public void movimentaSonda(char comando) {
        CoordenadaEntity ultima = ultimaCoordenada();
        switch (comando) {
            case 'M':
                andarParaFrente(ultima);
                break;
            case 'L':
                giraParaEsquerda(ultima);
                break;
            case 'R':
                giraParaDireita(ultima);
                break;
            default:
                break;
        }
    }

    private void andarParaFrente(CoordenadaEntity ultima) {
        Integer novoX = ultima.getX();
        Integer novoY = ultima.getY();
        
        switch (ultima.getDirecao()) {
            case "E":
                if (ultima.getX() < this.tamanho)
                    novoX++;
                break;
            case "S":
                if (ultima.getY() > 1)
                    novoY--;
                break;
            case "W":
                if (ultima.getX() > 1)
                    novoX--;
                break;
            case "N":
                if (ultima.getY() < this.tamanho)
                    novoY++;
                break;
            default:
                break;
        }
    
        this.addCoordenada(new CoordenadaEntity(novoX, novoY, ultima.getDirecao()));
    }

    private void giraParaEsquerda(CoordenadaEntity ultima) {
        String novaDirecao = ultima.getDirecao();
        
        switch (ultima.getDirecao()) {
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
    
        this.addCoordenada(new CoordenadaEntity(ultima.getX(), ultima.getY(), novaDirecao));
    }

    private void giraParaDireita(CoordenadaEntity ultima) {
        String novaDirecao = ultima.getDirecao();
        
        switch (ultima.getDirecao()) {
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
    
        this.addCoordenada(new CoordenadaEntity(ultima.getX(), ultima.getY(), novaDirecao));
    }
}
