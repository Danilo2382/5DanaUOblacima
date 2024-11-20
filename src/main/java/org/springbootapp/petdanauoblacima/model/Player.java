package org.springbootapp.petdanauoblacima.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String nickname;

    private Integer wins = 0;
    private Integer losses = 0;
    private Integer elo = 0;
    private Integer hoursPlayed = 0;

    @ManyToOne
    private Team team;

    private Integer ratingAdjustment = 50;
}
