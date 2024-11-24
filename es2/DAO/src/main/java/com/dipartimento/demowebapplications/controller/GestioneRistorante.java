package com.dipartimento.demowebapplications.controller;

import com.dipartimento.demowebapplications.model.Restaurant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GestioneRistorante {
    @GetMapping("/addRistorante")
    public String aggiungiRistorante(@RequestBody Restaurant ristorante) {
        System.out.println("ristorante: " + ristorante.getName());
        return "OK";
    }


}
