package com.MariaEduardaWendelMaia.BallitChampionship.entity;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Phase {
    private Integer id;
    private List<Match> matches;
    private boolean complete;
}

