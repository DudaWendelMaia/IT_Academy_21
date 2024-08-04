package com.MariaEduardaWendelMaia.BallitChampionship.repository;

import com.MariaEduardaWendelMaia.BallitChampionship.entity.Match;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MatchRepository {
    private static List<Match> matchList = new ArrayList<>();
    private AtomicInteger COUNTER = new AtomicInteger();

    public MatchRepository() {
    }

    public Match save(Match match) {
        if (match.getId() == null) {
            match.setId(COUNTER.incrementAndGet());
        }
        matchList.add(match);
        return match;
    }

    public Optional<Match> findById(Integer id) {
        return matchList.stream().filter(match -> match.getId().equals(id)).findFirst();
    }

    public void deleteById(Integer id) {
        matchList.removeIf(match -> match.getId().equals(id));
    }

    public List<Match> findAll() {
        return matchList;
    }
}
