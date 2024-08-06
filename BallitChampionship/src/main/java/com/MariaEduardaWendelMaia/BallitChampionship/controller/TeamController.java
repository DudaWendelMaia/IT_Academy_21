package com.MariaEduardaWendelMaia.BallitChampionship.controller;

import com.MariaEduardaWendelMaia.BallitChampionship.dto.TeamCreateDTO;
import com.MariaEduardaWendelMaia.BallitChampionship.dto.TeamDTO;
import com.MariaEduardaWendelMaia.BallitChampionship.entity.Match;
import com.MariaEduardaWendelMaia.BallitChampionship.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getMatchById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(teamService.getTeam(id));
    }

    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@Valid @RequestBody TeamCreateDTO teamCreateDTO) {
        return ResponseEntity.ok(teamService.create(teamCreateDTO));
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> listTeams() {
        return ResponseEntity.ok(teamService.list());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Integer id, @Valid @RequestBody TeamCreateDTO teamCreateDTO) throws Exception {
        return ResponseEntity.ok(teamService.update(id, teamCreateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Integer id) throws Exception {
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
