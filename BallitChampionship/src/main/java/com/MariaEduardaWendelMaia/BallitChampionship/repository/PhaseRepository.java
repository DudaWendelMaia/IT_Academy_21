package com.MariaEduardaWendelMaia.BallitChampionship.repository;

import com.MariaEduardaWendelMaia.BallitChampionship.entity.Phase;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PhaseRepository {
    private static List<Phase> phaseList = new ArrayList<>();
    private AtomicInteger COUNTER = new AtomicInteger();

    public PhaseRepository() {
    }

    public Phase save(Phase phase) {
        if (phase.getId() == null) {
            phase.setId(COUNTER.incrementAndGet());
        }
        phaseList.add(phase);
        return phase;
    }

    public Optional<Phase> findById(Integer id) {
        return phaseList.stream().filter(phase -> phase.getId().equals(id)).findFirst();
    }

    public void deleteById(Integer id) {
        phaseList.removeIf(phase -> phase.getId().equals(id));
    }

    public List<Phase> findAll() {
        return phaseList;
    }
}
