package br.com.thander.sonda.exception;

public class ParametroInvalidoException extends javax.persistence.EntityNotFoundException {
    
    public ParametroInvalidoException() {
    }
    
    public ParametroInvalidoException(String message) {
        super(message);
    }
}
