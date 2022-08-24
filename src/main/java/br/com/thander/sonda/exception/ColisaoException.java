package br.com.thander.sonda.exception;

import br.com.thander.sonda.model.entity.CoordenadaEntity;

public class ColisaoException extends Exception {
    
    public ColisaoException() {
    }
    
    public ColisaoException(String erro) {
        super(erro);
    }
    
    public ColisaoException(CoordenadaEntity coordenada) {
        super(String.format("As coordenadas x=%d, y=%d já estão ocupadas por outra sonda.",
                coordenada.getX(), coordenada.getY()));
    }
}
