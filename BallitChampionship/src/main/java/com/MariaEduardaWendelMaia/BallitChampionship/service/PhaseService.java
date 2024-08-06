package com.MariaEduardaWendelMaia.BallitChampionship.service;

import com.MariaEduardaWendelMaia.BallitChampionship.dto.PhaseDTO;
import com.MariaEduardaWendelMaia.BallitChampionship.entity.Match;
import com.MariaEduardaWendelMaia.BallitChampionship.entity.Phase;
import com.MariaEduardaWendelMaia.BallitChampionship.repository.PhaseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhaseService {

    private final PhaseRepository phaseRepository;
    private final ObjectMapper objectMapper;

    public PhaseDTO create(PhaseDTO phaseDTO) {
        Phase phase = objectMapper.convertValue(phaseDTO, Phase.class);
        Phase phaseCreated = phaseRepository.save(phase);
        return objectMapper.convertValue(phaseCreated, PhaseDTO.class);
    }

    public List<PhaseDTO> list() {
        return phaseRepository.findAll().stream()
                .map(phase -> objectMapper.convertValue(phase, PhaseDTO.class))
                .collect(Collectors.toList());
    }

    public PhaseDTO update(Integer id, PhaseDTO phaseDTO) throws Exception {
        Phase phaseRetrieved = phaseRepository.findById(id)
                .orElseThrow(() -> new Exception("Phase not found!"));
        phaseRetrieved.setMatches(phaseDTO.getMatches().stream()
                .map(matchDTO -> objectMapper.convertValue(matchDTO, Match.class))
                .collect(Collectors.toList()));
        phaseRetrieved.setComplete(phaseDTO.isComplete());

        Phase phaseUpdated = phaseRepository.save(phaseRetrieved);
        return objectMapper.convertValue(phaseUpdated, PhaseDTO.class);
    }

    public void delete(Integer id) throws Exception {
        phaseRepository.deleteById(id);
    }

    public PhaseDTO getPhase(Integer id) throws Exception {
        return objectMapper.convertValue(phaseRepository.findById(id)
                .orElseThrow(() -> new Exception("Phase not found!")), PhaseDTO.class);
    }
}
