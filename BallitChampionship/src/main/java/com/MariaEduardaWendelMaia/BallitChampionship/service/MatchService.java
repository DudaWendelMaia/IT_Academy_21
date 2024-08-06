package com.MariaEduardaWendelMaia.BallitChampionship.service;

import com.MariaEduardaWendelMaia.BallitChampionship.dto.MatchDTO;
import com.MariaEduardaWendelMaia.BallitChampionship.entity.Match;
import com.MariaEduardaWendelMaia.BallitChampionship.repository.MatchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final ObjectMapper objectMapper;

    public MatchDTO create(MatchDTO matchDTO) {
        Match match = objectMapper.convertValue(matchDTO, Match.class);
        Match matchCreated = matchRepository.save(match);
        return objectMapper.convertValue(matchCreated, MatchDTO.class);
    }

    public List<MatchDTO> list() {
        return matchRepository.findAll().stream()
                .map(match -> objectMapper.convertValue(match, MatchDTO.class))
                .collect(Collectors.toList());
    }

    public MatchDTO update(Integer id, MatchDTO matchDTO) throws Exception {
        Match matchRetrieved = matchRepository.findById(id)
                .orElseThrow(() -> new Exception("Match not found!"));
        matchRetrieved.setPointsTeamA(matchDTO.getPointsTeamA());
        matchRetrieved.setPointsTeamB(matchDTO.getPointsTeamB());
        matchRetrieved.setFinished(matchDTO.isFinished());

        Match matchUpdated = matchRepository.save(matchRetrieved);
        return objectMapper.convertValue(matchUpdated, MatchDTO.class);
    }

    public void delete(Integer id) throws Exception {
        matchRepository.deleteById(id);
    }

    public MatchDTO getMatch(Integer id) throws Exception {
        return objectMapper.convertValue(matchRepository.findById(id)
                .orElseThrow(() -> new Exception("Match not found!")), MatchDTO.class);
    }

    public MatchDTO registerBlot(Integer id, String team) throws Exception {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new Exception("Match not found!"));
        if (team.equals("A")) {
            match.setPointsTeamA(match.getPointsTeamA() + 5);
        } else if (team.equals("B")) {
            match.setPointsTeamB(match.getPointsTeamB() + 5);
        }
        Match updatedMatch = matchRepository.save(match);
        return objectMapper.convertValue(updatedMatch, MatchDTO.class);
    }

    public MatchDTO registerPlif(Integer id, String team) throws Exception {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new Exception("Match not found!"));
        if (team.equals("A")) {
            match.setPointsTeamA(match.getPointsTeamA() + 1);
        } else if (team.equals("B")) {
            match.setPointsTeamB(match.getPointsTeamB() + 1);
        }
        Match updatedMatch = matchRepository.save(match);
        return objectMapper.convertValue(updatedMatch, MatchDTO.class);
    }

    public MatchDTO finishMatch(Integer id) throws Exception {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new Exception("Match not found!"));
        match.setFinished(true);
        Match updatedMatch = matchRepository.save(match);
        return objectMapper.convertValue(updatedMatch, MatchDTO.class);
    }
}
