package com.MariaEduardaWendelMaia.BallitChampionship.dto;

import lombok.Data;

@Data
public class MatchDTO {
    private Integer id;
    private TeamDTO teamA;
    private TeamDTO teamB;
    private int pointsTeamA;
    private int pointsTeamB;
    private boolean finished;
    private String motivationalMessage;
}
