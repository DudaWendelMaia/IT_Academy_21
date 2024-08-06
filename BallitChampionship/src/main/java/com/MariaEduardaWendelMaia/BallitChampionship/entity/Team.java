package com.MariaEduardaWendelMaia.BallitChampionship.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private String warCry;
    private int foundationYear;
    private int points;
    private int totalBlots;
    private int totalPlifs;
    private int totalAdvrunghs;
}

