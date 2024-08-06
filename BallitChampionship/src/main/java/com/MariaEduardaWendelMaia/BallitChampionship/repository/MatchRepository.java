package com.MariaEduardaWendelMaia.BallitChampionship.repository;

import com.MariaEduardaWendelMaia.BallitChampionship.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {
}
