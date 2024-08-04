package com.MariaEduardaWendelMaia.BallitChampionship.controller;

import com.MariaEduardaWendelMaia.BallitChampionship.dto.PhaseDTO;
import com.MariaEduardaWendelMaia.BallitChampionship.service.PhaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phases")
@RequiredArgsConstructor
public class PhaseController {

    private final PhaseService phaseService;

    @PostMapping
    public ResponseEntity<PhaseDTO> createPhase(@Valid @RequestBody PhaseDTO phaseDTO) {
        return ResponseEntity.ok(phaseService.create(phaseDTO));
    }

    @GetMapping
    public ResponseEntity<List<PhaseDTO>> listPhases() {
        return ResponseEntity.ok(phaseService.list());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhaseDTO> updatePhase(@PathVariable Integer id, @Valid @RequestBody PhaseDTO phaseDTO) throws Exception {
        return ResponseEntity.ok(phaseService.update(id, phaseDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhase(@PathVariable Integer id) throws Exception {
        phaseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
