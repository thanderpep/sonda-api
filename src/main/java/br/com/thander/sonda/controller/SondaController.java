package br.com.thander.sonda.controller;

import br.com.thander.sonda.exception.ColisaoException;
import br.com.thander.sonda.model.dto.ComandoDTO;
import br.com.thander.sonda.model.dto.RetornoDTO;
import br.com.thander.sonda.model.dto.SondaDTO;
import br.com.thander.sonda.service.SondaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sonda")
public class SondaController {
    
    @Autowired
    private SondaService sondaService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RetornoDTO criaSonda(@RequestBody @Valid SondaDTO sondaDTO, BindingResult errors) throws ColisaoException {
        if (errors.hasErrors())
            throw new IllegalArgumentException(errors.getFieldError().getDefaultMessage());
        return sondaService.criaSonda(sondaDTO);
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RetornoDTO buscaSonda(@PathVariable("id") Long sondaId) {
        return sondaService.buscaSondaPorId(sondaId);
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RetornoDTO> buscaTodasSondas() {
        return sondaService.buscaTodasSondas();
    }
    
    
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RetornoDTO movimentaSonda(@PathVariable("id") Long sondaId, @RequestBody @Valid ComandoDTO comandoDTO, BindingResult errors) {
        if (errors.hasErrors())
            throw new IllegalArgumentException(errors.getFieldError().getDefaultMessage());
        return sondaService.movimentaSondaPorId(sondaId, comandoDTO);
    }
}
