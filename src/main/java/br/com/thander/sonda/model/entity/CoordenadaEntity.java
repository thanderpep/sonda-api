package br.com.thander.sonda.model.entity;

import br.com.thander.sonda.model.dto.CoordenadaDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/***
 * Classe responsável por armazenar coordenadas de uma sonda
 */
@Getter
@Entity
@Table(name = "coordenada")
@NoArgsConstructor
@AllArgsConstructor
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
    
    @Column(length = 1)
    private String comando;
    
    @JsonBackReference
    @Setter
    @ManyToOne
    @JoinColumn(name="sonda_id", nullable=false)
    private SondaEntity sonda;
    
    @Column(columnDefinition = "timestamp default current_timestamp", insertable = false)
    private Timestamp dataHora;
    
    public CoordenadaEntity(Integer x, Integer y, String direcao, String comando) {
        this.x = x;
        this.y = y;
        this.direcao = direcao;
        this.comando = comando;
    }
    
    /***
     * Calcula a próxima coordenada a partir de um comando a ser executado
     * @param comando caracteres M, L ou R
     * @return CoordenadaEntity contendo os dados da próxima coordenada
     */
    public CoordenadaEntity calculaProximaCoordenada(char comando) {
        switch (comando) {
            case 'M':
                return andarParaFrente(comando);
            case 'L':
                return giraParaEsquerda(comando);
            case 'R':
                return giraParaDireita(comando);
            default:
                return this;
        }
    }
    
    private CoordenadaEntity andarParaFrente(char comando) {
        int novoX = this.x;
        int novoY = this.y;
        
        switch (this.direcao) {
            case "E":
                if (this.x < 5)
                    novoX++;
                break;
            case "S":
                if (this.y > 0)
                    novoY--;
                break;
            case "W":
                if (this.x > 0)
                    novoX--;
                break;
            case "N":
                if (this.y < 5)
                    novoY++;
                break;
            default:
                break;
        }
    
        CoordenadaEntity proxCoordenada = new CoordenadaEntity(novoX, novoY, this.direcao, String.valueOf(comando));
        proxCoordenada.setSonda(this.sonda);
        return proxCoordenada;
    }
    
    private CoordenadaEntity giraParaEsquerda(char comando) {
        String novaDirecao = this.direcao;
        
        switch (this.direcao) {
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
    
        CoordenadaEntity proxCoordenada = new CoordenadaEntity(this.x, this.y, novaDirecao, String.valueOf(comando));
        proxCoordenada.setSonda(this.sonda);
        return proxCoordenada;
    }
    
    private CoordenadaEntity giraParaDireita(char comando) {
        String novaDirecao = this.direcao;
        
        switch (novaDirecao) {
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
    
        CoordenadaEntity proxCoordenada = new CoordenadaEntity(this.x, this.y, novaDirecao, String.valueOf(comando));
        proxCoordenada.setSonda(this.sonda);
        return proxCoordenada;
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
                return "direção inválida";
        }
    }
    
    public CoordenadaDTO converteParaCoordenadaDTO(){
        return new CoordenadaDTO(this.x, this.y, this.direcao, this.comando);
    }
}
