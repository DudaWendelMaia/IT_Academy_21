package com.MariaEduardaWendelMaia.BallitChampionship.repository;

import com.MariaEduardaWendelMaia.BallitChampionship.entity.Team;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TeamRepository {
    private static List<Team> teamList = new ArrayList<>();
    private AtomicInteger COUNTER = new AtomicInteger();

    public TeamRepository() {
        teamList.add(new Team(COUNTER.incrementAndGet(), "Team A", "Go A!", 1990, 0, 0, 0, 0));
        teamList.add(new Team(COUNTER.incrementAndGet(), "Team B", "Go B!", 1991, 0, 0, 0, 0));
        teamList.add(new Team(COUNTER.incrementAndGet(), "Team C", "Go C!", 1992, 0, 0, 0, 0));
        teamList.add(new Team(COUNTER.incrementAndGet(), "Team D", "Go D!", 1993, 0, 0, 0, 0));
        teamList.add(new Team(COUNTER.incrementAndGet(), "Team E", "Go E!", 1994, 0, 0, 0, 0));
        teamList.add(new Team(COUNTER.incrementAndGet(), "Team F", "Go F!", 1995, 0, 0, 0, 0));
        teamList.add(new Team(COUNTER.incrementAndGet(), "Team G", "Go G!", 1996, 0, 0, 0, 0));
        teamList.add(new Team(COUNTER.incrementAndGet(), "Team H", "Go H!", 1997, 0, 0, 0, 0));
    }

    public Team save(Team team) {
        if (team.getId() == null) {
            team.setId(COUNTER.incrementAndGet());
        }
        teamList.add(team);
        return team;
    }

    public Optional<Team> findById(Integer id) {
        return teamList.stream().filter(team -> team.getId().equals(id)).findFirst();
    }

    public void deleteById(Integer id) {
        teamList.removeIf(team -> team.getId().equals(id));
    }

    public List<Team> findAll() {
        return teamList;
    }
}
