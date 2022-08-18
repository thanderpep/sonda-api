package br.com.thander.sonda.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomErrorResponse {
    
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
