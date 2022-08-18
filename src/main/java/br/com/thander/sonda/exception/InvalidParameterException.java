package br.com.thander.sonda.exception;

public class InvalidParameterException extends javax.persistence.EntityNotFoundException {
    
    public InvalidParameterException() {
    }
    
    public InvalidParameterException(String message) {
        super(message);
    }
}
