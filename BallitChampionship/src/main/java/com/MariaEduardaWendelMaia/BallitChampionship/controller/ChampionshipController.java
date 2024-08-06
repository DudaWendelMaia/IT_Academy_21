package com.MariaEduardaWendelMaia.BallitChampionship.controller;

import com.MariaEduardaWendelMaia.BallitChampionship.dto.TeamDTO;
import com.MariaEduardaWendelMaia.BallitChampionship.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/championship")
@RequiredArgsConstructor
public class ChampionshipController {

    private final TeamService teamService;

    @PostMapping("/start")
    public ResponseEntity<String> startChampionship() {
        try {
            return ResponseEntity.ok(teamService.startChampionship());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/results")
    public ResponseEntity<List<TeamDTO>> getFinalResults() {
        return ResponseEntity.ok(teamService.getFinalResults());
    }

    @GetMapping("/champion")
    public ResponseEntity<TeamDTO> getChampion() {
        return ResponseEntity.ok(teamService.getChampion());
    }
}

