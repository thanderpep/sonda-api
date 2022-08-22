package br.com.thander.sonda.controller;

import br.com.thander.sonda.exception.ParametroInvalidoException;
import br.com.thander.sonda.model.dto.EntradaSondaDTO;
import br.com.thander.sonda.model.dto.RetornoSondaDTO;
import br.com.thander.sonda.service.SondaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sonda")
public class SondaController {
    
    @Autowired
    private SondaService sondaService;
    
    @PostMapping
    public ResponseEntity<List<?>> lancaSonda(
            @RequestBody @Valid List<EntradaSondaDTO> entradaSondaDTOLista, BindingResult errors) {
        
        // Valida a entrada de dados do usuário
        if (errors.hasErrors())
            throw new ParametroInvalidoException(errors.getFieldError().getDefaultMessage());
    
        List<?> sondas = sondaService.lancaSonda(entradaSondaDTOLista);
        return new ResponseEntity<>(sondas, HttpStatus.CREATED);
    }
}
