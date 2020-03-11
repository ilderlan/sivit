package br.gov.caixa.sivit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/init")
public class SivitInitController extends SpringServletContainerInitializer {

    @GetMapping
    public ResponseEntity<?> init(){
        return ResponseEntity.ok("Bem vindo a autenticação com public key");
    }
}
