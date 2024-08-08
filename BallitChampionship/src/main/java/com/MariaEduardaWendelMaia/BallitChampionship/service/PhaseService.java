package com.MariaEduardaWendelMaia.BallitChampionship.service;

import com.MariaEduardaWendelMaia.BallitChampionship.dto.PhaseDTO;
import com.MariaEduardaWendelMaia.BallitChampionship.entity.Match;
import com.MariaEduardaWendelMaia.BallitChampionship.entity.Phase;
import com.MariaEduardaWendelMaia.BallitChampionship.entity.Team;
import com.MariaEduardaWendelMaia.BallitChampionship.repository.MatchRepository;
import com.MariaEduardaWendelMaia.BallitChampionship.repository.PhaseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhaseService {

    private final PhaseRepository phaseRepository;
    private final MatchRepository matchRepository;
    private final ObjectMapper objectMapper;
    private final Random random;

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

    public PhaseDTO createNextPhase() throws Exception {
        List<Match> previousMatches = matchRepository.findAll().stream()
                .filter(Match::isFinished)
                .collect(Collectors.toList());

        List<Team> advancingTeams = new ArrayList<>();
        for (Match match : previousMatches) {
            if (match.getPointsTeamA() > match.getPointsTeamB()) {
                advancingTeams.add(match.getTeamA());
            } else if (match.getPointsTeamB() > match.getPointsTeamA()) {
                advancingTeams.add(match.getTeamB());
            } else {
                //On draw, randomize which team wins
                advancingTeams.add(random.nextBoolean() ? match.getTeamA() : match.getTeamB());
            }
        }

        if (advancingTeams.size() < 2) {
            throw new Exception("Fase finalizada. Nenhuma próxima fase necessária.");
        }

        Collections.shuffle(advancingTeams);
        List<Match> newMatches = new ArrayList<>();
        for (int i = 0; i < advancingTeams.size(); i += 2) {
            Match match = new Match();
            match.setTeamA(advancingTeams.get(i));
            match.setTeamB(advancingTeams.get(i + 1));
            match.setPointsTeamA(50);
            match.setPointsTeamB(50);
            match.setFinished(false);
            newMatches.add(match);
        }

        Phase newPhase = new Phase();
        newPhase.setMatches(newMatches);
        newPhase.setComplete(false);
        Phase createdPhase = phaseRepository.save(newPhase);

        return objectMapper.convertValue(createdPhase, PhaseDTO.class);
    }

    public void completePhase(Integer phaseId) throws Exception {
        Phase phase = phaseRepository.findById(phaseId)
                .orElseThrow(() -> new Exception("Phase not found!"));

        boolean allMatchesFinished = phase.getMatches().stream()
                .allMatch(Match::isFinished);

        if (!allMatchesFinished) {
            throw new Exception("Nem todas as partidas da fase foram finalizadas.");
        }

        phase.setComplete(true);
        phaseRepository.save(phase);
    }
}
