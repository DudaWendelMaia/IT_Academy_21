package com.MariaEduardaWendelMaia.BallitChampionship.dto;

import lombok.Data;

import java.util.List;

@Data
public class PhaseDTO {
    private Integer id;
    private List<MatchDTO> matches;
    private boolean complete;
}
