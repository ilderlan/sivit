package br.gov.caixa.sivit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/init")
public class SivitInitController {

    @GetMapping
    @Secured({"ROLE_NOP_ADMIN"})
    public ResponseEntity<?> init(){
        return ResponseEntity.ok("Validando token com a chave pública");
    }
}
