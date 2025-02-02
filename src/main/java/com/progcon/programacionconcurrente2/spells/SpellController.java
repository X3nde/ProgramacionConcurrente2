package com.progcon.programacionconcurrente2.spells;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spells")
public class SpellController {
    @Autowired
    private SpellService spellService;

    @GetMapping("/cast")
    public String castSpell(@RequestParam String spell) {
        return spellService.castSpell(spell);
    }
}