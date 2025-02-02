package com.progcon.programacionconcurrente2.spells;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.client.RestTemplate;

@Service
public class SpellService {
    @HystrixCommand(fallbackMethod = "defaultSpell")
    public String castSpell(String spellName) {
        RestOperations restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://spell-service/cast?spell=" + spellName, String.class);
    }
    public String defaultSpell(String spellName) {
        return "The spell " + spellName + " is unavailable.";
    }
}