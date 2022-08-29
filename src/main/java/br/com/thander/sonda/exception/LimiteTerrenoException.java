package br.com.thander.sonda.exception;

import br.com.thander.sonda.model.entity.CoordenadaEntity;

public class LimiteTerrenoException extends Exception {
    
    public LimiteTerrenoException() {
    }
    
    public LimiteTerrenoException(String erro) {
        super(erro);
    }
    
    public LimiteTerrenoException(CoordenadaEntity coordenada) {
        super(String.format("As coordenadas x=%d, y=%d excedem os limites do terreno.",
                coordenada.getX(), coordenada.getY()));
    }
}
