package com.MariaEduardaWendelMaia.BallitChampionship.controller;


import com.MariaEduardaWendelMaia.BallitChampionship.dto.MatchDTO;
import com.MariaEduardaWendelMaia.BallitChampionship.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> getMatchById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(matchService.getMatch(id));
    }

    @PostMapping
    public ResponseEntity<MatchDTO> createMatch(@Valid @RequestBody MatchDTO matchDTO) {
        return ResponseEntity.ok(matchService.create(matchDTO));
    }

    @GetMapping
    public ResponseEntity<List<MatchDTO>> listMatches() {
        return ResponseEntity.ok(matchService.list());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchDTO> updateMatch(@PathVariable Integer id, @Valid @RequestBody MatchDTO matchDTO) throws Exception {
        return ResponseEntity.ok(matchService.update(id, matchDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Integer id) throws Exception {
        matchService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/blot/{team}")
    public ResponseEntity<MatchDTO> registerBlot(@PathVariable Integer id, @PathVariable String team) throws Exception {
        return ResponseEntity.ok(matchService.registerBlot(id, team));
    }

    @PostMapping("/{id}/plif/{team}")
    public ResponseEntity<MatchDTO> registerPlif(@PathVariable Integer id, @PathVariable String team) throws Exception {
        return ResponseEntity.ok(matchService.registerPlif(id, team));
    }

    @PostMapping("/{id}/finish")
    public ResponseEntity<MatchDTO> finishMatch(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(matchService.finishMatch(id));
    }
}
