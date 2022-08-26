package br.com.thander.sonda.model.entity;

import br.com.thander.sonda.model.dto.CoordenadaDTO;
import br.com.thander.sonda.model.dto.RetornoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/***
 * Classe responsável por armazenar dados de uma sonda
 */
@Getter
@Entity
@Table(name = "sonda")
@NoArgsConstructor
@AllArgsConstructor
public class SondaEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Integer inicialX;
    
    @Column(nullable = false)
    private Integer inicialY;
    
    @Column(length = 1, nullable = false)
    private String direcaoInical;
    
    @Column(nullable = false)
    private Integer atualX;
    
    @Column(nullable = false)
    private Integer atualY;
    
    @Column(length = 1, nullable = false)
    private String direcaoAtual;
    
    @Column(name = "planeta", nullable = false)
    private String planeta;
    
    @Setter
    private String ultimoComando;
    
    @OneToMany(mappedBy = "sonda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoordenadaEntity> coordenadas = new ArrayList<>();
    
    public SondaEntity(Integer inicialX, Integer inicialY, String direcaoInical, String planeta, String ultimoComando) {
        this.inicialX = inicialX;
        this.inicialY = inicialY;
        this.direcaoInical = direcaoInical.toUpperCase();
        this.atualX = inicialX;
        this.atualY = inicialY;
        this.direcaoAtual = direcaoInical.toUpperCase();
        this.planeta = planeta.toUpperCase();
        this.ultimoComando = ultimoComando;
        this.addCoordenada(new CoordenadaEntity(inicialX, inicialY, direcaoInical.toUpperCase(), null));
    }
    
    public CoordenadaEntity coordenadaAtual() {
        if (this.coordenadas != null && !this.coordenadas.isEmpty()) {
            return this.coordenadas.get(this.coordenadas.size()-1);
        }
        return null;
    }
    
    public void addCoordenada(CoordenadaEntity coordenada) {
        this.atualX = coordenada.getX();
        this.atualY = coordenada.getY();
        this.direcaoAtual = coordenada.getDirecao();
        this.coordenadas.add(coordenada);
        coordenada.setSonda(this);
    }
    
    /***
     * Calcula a próxima coordenada a partir de um comando a ser executado e move a sonda para a posição calculada
     * @param comando caracteres M, L ou R
     */
    public void mover(char comando) {
        CoordenadaEntity novaCoordenada = coordenadaAtual().calculaProximaCoordenada(comando);
        this.addCoordenada(novaCoordenada);
    }
    
    /***
     * Converte um objeto SondaEntity para RetornoDTO com dados de uma sonda e seus movimentos
     * @return RetornoDTO com dados de uma sonda e seus movimentos caso tenha
     */
    public RetornoDTO converteParaRetorno(){
        return this.converteParaRetorno(null);
    }
    
    /***
     * Converte um objeto SondaEntity para RetornoDTO com dados de uma sonda que colidiu
     * com outra a se movimentar
     * @param erro String com mensagem de erro
     * @return RetornoDTO com dados de colisão de uma sonda ao movimentar para uma coordenada já ocupada
     */
    public RetornoDTO converteParaRetorno(String erro){
        List<CoordenadaDTO> coordenadaDTOLista = coordenadas.stream()
                .map(CoordenadaEntity::converteParaCoordenadaDTO).collect(Collectors.toList());
        return new RetornoDTO(this.id, this.inicialX, this.inicialY, this.direcaoInical,
                this.atualX, this.atualY, this.direcaoAtual, this.planeta, this.ultimoComando,
                coordenadaDTOLista, erro);
    }
    
    /***
     * Converte um objeto SondaEntity para RetornoDTO com dados de uma sonda e seus movimentos
     * @param indexCoordenadas Index inical das coordenadas que deseja no retorno
     * @return RetornoDTO com dados de uma sonda e seus movimentos caso tenha
     */
    public RetornoDTO converteParaRetornoPut(int indexCoordenadas) {
        return this.converteParaRetornoPut(indexCoordenadas, null);
    }
    
    /***
     * Converte um objeto SondaEntity para RetornoDTO com dados de uma sonda que colidiu
     * com outra a se movimentar
     * @param erro String com mensagem de erro
     * @return RetornoDTO com dados de colisão de uma sonda ao movimentar para uma coordenada já ocupada
     */
    public RetornoDTO converteParaRetornoPut(int indexCoordenadas, String erro){
        CoordenadaEntity coordInicial = coordenadas.get(indexCoordenadas);
    
        List<CoordenadaDTO> coordenadaDTOLista = coordenadas.stream()
                .map(CoordenadaEntity::converteParaCoordenadaDTO).collect(Collectors.toList())
                .subList(++indexCoordenadas, coordenadas.size());
    
        return new RetornoDTO(this.id, coordInicial.getX(), coordInicial.getY(), coordInicial.getDirecao(),
                this.atualX, this.atualY, this.direcaoAtual, this.planeta, this.ultimoComando, coordenadaDTOLista, erro);
    }
}
