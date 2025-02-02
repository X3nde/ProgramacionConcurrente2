package com.progcon.programacionconcurrente2.dinosaurs;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public class DinosaurService {
    public Flux<DinosaurData> getDinosaurData() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> new DinosaurData("T-Rex", 36.5 + Math.random()));
    }
}
