package com.MariaEduardaWendelMaia.BallitChampionship.repository;

import com.MariaEduardaWendelMaia.BallitChampionship.entity.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Integer> {
}
