package com.progcon.programacionconcurrente2.dinosaurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/dinosaurs")
public class DinosaurController {
    @Autowired
    private DinosaurService dinosaurService;

    @GetMapping("/data")
    public Flux<DinosaurData> getDinosaurData() {
        return dinosaurService.getDinosaurData();
    }
}