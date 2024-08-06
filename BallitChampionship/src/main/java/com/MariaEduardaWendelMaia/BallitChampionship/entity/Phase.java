package com.MariaEduardaWendelMaia.BallitChampionship.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Match> matches;

    private boolean complete;
}

