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
        Match matchRetrieved = getMatch(id);
        matchRetrieved.setPointsTeamA(matchDTO.getPointsTeamA());
        matchRetrieved.setPointsTeamB(matchDTO.getPointsTeamB());
        matchRetrieved.setFinished(matchDTO.isFinished());

        Match matchUpdated = matchRepository.save(matchRetrieved);
        return objectMapper.convertValue(matchUpdated, MatchDTO.class);
    }

    public void delete(Integer id) throws Exception {
        Match matchRetrieved = getMatch(id);
        matchRepository.deleteById(matchRetrieved.getId());
    }

    private Match getMatch(Integer id) throws Exception {
        return matchRepository.findById(id)
                .orElseThrow(() -> new Exception("Match not found!"));
    }
}
