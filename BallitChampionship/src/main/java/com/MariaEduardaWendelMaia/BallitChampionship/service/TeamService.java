package com.MariaEduardaWendelMaia.BallitChampionship.service;

import com.MariaEduardaWendelMaia.BallitChampionship.dto.TeamCreateDTO;
import com.MariaEduardaWendelMaia.BallitChampionship.dto.TeamDTO;
import com.MariaEduardaWendelMaia.BallitChampionship.entity.Match;
import com.MariaEduardaWendelMaia.BallitChampionship.entity.Phase;
import com.MariaEduardaWendelMaia.BallitChampionship.entity.Team;
import com.MariaEduardaWendelMaia.BallitChampionship.repository.MatchRepository;
import com.MariaEduardaWendelMaia.BallitChampionship.repository.PhaseRepository;
import com.MariaEduardaWendelMaia.BallitChampionship.repository.TeamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final PhaseRepository phaseRepository;
    private final MatchRepository matchRepository;
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
                .orElseThrow(() -> new Exception("Time não encontrado!"));
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
                .orElseThrow(() -> new Exception("Time não encontrado!")), TeamDTO.class);
    }

    public String startChampionship() throws Exception {
        List<Team> teams = teamRepository.findAll();
        if (teams.size() != 8 && teams.size() != 16) {
            throw new Exception("Número inválido de times. Deve ser entre 8 e 16 e um número par.");
        }

        Collections.shuffle(teams);
        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < teams.size(); i += 2) {
            Match match = new Match();
            match.setTeamA(teams.get(i));
            match.setTeamB(teams.get(i + 1));
            match.setPointsTeamA(50);
            match.setPointsTeamB(50);
            match.setFinished(false);
            matches.add(match);
        }
        Phase phase = new Phase();
        phase.setMatches(matches);
        phase.setComplete(false);
        phaseRepository.save(phase);

        return "Campeonato iniciado com sucesso!";
    }

    public List<TeamDTO> getFinalResults() {
        List<Team> teams = teamRepository.findAll();
        teams.forEach(this::calculateTotalPoints);
        teams.sort((team1, team2) -> Integer.compare(team2.getPoints(), team1.getPoints()));
        return teams.stream()
                .map(team -> objectMapper.convertValue(team, TeamDTO.class))
                .collect(Collectors.toList());
    }

    public TeamDTO getChampion() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream()
                .max((team1, team2) -> Integer.compare(team1.getPoints(), team2.getPoints()))
                .map(team -> objectMapper.convertValue(team, TeamDTO.class))
                .orElse(null);
    }

    public TeamDTO registerAdvrungh(Integer id) throws Exception {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new Exception("Time não encontrado!"));
        team.setPoints(team.getPoints() - 10);
        team.setTotalAdvrunghs(team.getTotalAdvrunghs() + 1);
        Team updatedTeam = teamRepository.save(team);
        return objectMapper.convertValue(updatedTeam, TeamDTO.class);
    }

    public void updateMatchPoints(Match match, String action, String team) {
        if ("blot".equals(action)) {
            if ("A".equals(team)) {
                match.setPointsTeamA(match.getPointsTeamA() + 5);
                match.getTeamA().setTotalBlots(match.getTeamA().getTotalBlots() + 1);
                match.getTeamA().setPoints(match.getTeamA().getPoints() + 5);
            } else if ("B".equals(team)) {
                match.setPointsTeamB(match.getPointsTeamB() + 5);
                match.getTeamB().setTotalBlots(match.getTeamB().getTotalBlots() + 1);
                match.getTeamB().setPoints(match.getTeamB().getPoints() + 5);
            }
        } else if ("plif".equals(action)) {
            if ("A".equals(team)) {
                match.setPointsTeamA(match.getPointsTeamA() + 1);
                match.getTeamA().setTotalPlifs(match.getTeamA().getTotalPlifs() + 1);
                match.getTeamA().setPoints(match.getTeamA().getPoints() + 1);
            } else if ("B".equals(team)) {
                match.setPointsTeamB(match.getPointsTeamB() + 1);
                match.getTeamB().setTotalPlifs(match.getTeamB().getTotalPlifs() + 1);
                match.getTeamB().setPoints(match.getTeamB().getPoints() + 1);
            }
        }
    }

    private void calculateTotalPoints(Team team) {
        team.setPoints(50 + (team.getTotalBlots() * 5) + team.getTotalPlifs() - (team.getTotalAdvrunghs() * 10));
    }
}
