package br.com.thander.sonda.controller;

import br.com.thander.sonda.model.dto.EntradaSondaDTO;
import br.com.thander.sonda.model.dto.RetornoSondaDTO;
import br.com.thander.sonda.service.SondaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/sonda")
public class SondaController {
    
    @Autowired
    private SondaService sondaService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<RetornoSondaDTO> criaSonda(
            @RequestBody @Valid List<EntradaSondaDTO> entradaSondaDTOLista, BindingResult errors) {
        // Valida a entrada de dados do usuário
//        if (errors.hasErrors())
//            throw new IllegalArgumentException(errors.getFieldError().getDefaultMessage());
        return sondaService.criaSonda(entradaSondaDTOLista);
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RetornoSondaDTO buscaSonda(@PathVariable("id") Long sondaId) {
        return sondaService.buscaSondaPorId(sondaId);
    }
}
