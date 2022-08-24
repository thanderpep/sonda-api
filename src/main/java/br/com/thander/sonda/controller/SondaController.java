package br.com.thander.sonda.controller;

import br.com.thander.sonda.model.dto.RetornoDTO;
import br.com.thander.sonda.model.dto.SondaDTO;
import br.com.thander.sonda.service.SondaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/sonda")
public class SondaController {
    
    @Autowired
    private SondaService sondaService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<RetornoDTO> criaSondas(@RequestBody @Valid List<SondaDTO> sondaDTOLista) {
        return sondaDTOLista.stream().map(sonda -> sondaService.criaSonda(sonda)).collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RetornoDTO buscaSonda(@PathVariable("id") Long sondaId) {
        return sondaService.buscaSondaPorId(sondaId);
    }
    
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RetornoDTO movimentaSonda(@PathVariable("id") Long sondaId, @RequestParam String comandos) {
        return sondaService.movimentaSondaPorId(sondaId, comandos);
    }
}
