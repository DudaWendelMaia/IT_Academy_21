package com.MariaEduardaWendelMaia.BallitChampionship.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Match {
    private Integer id;
    private Team teamA;
    private Team teamB;
    private int pointsTeamA;
    private int pointsTeamB;
    private boolean finished;
}
