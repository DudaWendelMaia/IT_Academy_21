package com.MariaEduardaWendelMaia.BallitChampionship.service;

import com.MariaEduardaWendelMaia.BallitChampionship.dto.TeamCreateDTO;
import com.MariaEduardaWendelMaia.BallitChampionship.dto.TeamDTO;
import com.MariaEduardaWendelMaia.BallitChampionship.entity.Team;
import com.MariaEduardaWendelMaia.BallitChampionship.repository.TeamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final ObjectMapper objectMapper;

    public TeamDTO create(TeamCreateDTO teamCreateDTO) {
        Team team = objectMapper.convertValue(teamCreateDTO, Team.class);
        Team teamCreated = teamRepository.save(team);
        return objectMapper.convertValue(teamCreated, TeamDTO.class);
    }

    public List<TeamDTO> list() {
        return teamRepository.findAll().stream()
                .map(team -> objectMapper.convertValue(team, TeamDTO.class))
                .collect(Collectors.toList());
    }

    public TeamDTO update(Integer id, TeamCreateDTO teamCreateDTO) throws Exception {
        Team teamRetrieved = teamRepository.findById(id)
                .orElseThrow(() -> new Exception("Team not found!"));
        teamRetrieved.setName(teamCreateDTO.getName());
        teamRetrieved.setWarCry(teamCreateDTO.getWarCry());
        teamRetrieved.setFoundationYear(teamCreateDTO.getFoundationYear());

        Team teamUpdated = teamRepository.save(teamRetrieved);
        return objectMapper.convertValue(teamUpdated, TeamDTO.class);
    }

    public void delete(Integer id) throws Exception {
        teamRepository.deleteById(id);
    }

    public TeamDTO getTeam(Integer id) throws Exception {
        return objectMapper.convertValue(teamRepository.findById(id)
                .orElseThrow(() -> new Exception("Team not found!")), TeamDTO.class);
    }
}
