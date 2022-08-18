package br.com.thander.sonda.controller;

import br.com.thander.sonda.exception.InvalidParameterException;
import br.com.thander.sonda.model.dto.SondaDTO;
import br.com.thander.sonda.model.entity.SondaEntity;
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

@RestController
@RequestMapping("/sonda")
public class SondaController {
    
    @Autowired
    private SondaService sondaService;
    
    @PostMapping
    public ResponseEntity<SondaEntity> lancaSonda(@RequestBody @Valid SondaDTO sondaDTO, BindingResult errors) {
        if (errors.hasErrors())
            throw new InvalidParameterException(errors.getFieldError().getDefaultMessage());
        SondaEntity sondaEntity = sondaService.create(sondaDTO.transformaParaObjeto());
        return new ResponseEntity<>(sondaEntity, HttpStatus.CREATED);
    }
}
