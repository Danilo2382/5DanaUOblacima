package org.springbootapp.petdanauoblacima.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private Team team1;

    @ManyToOne
    private Team team2;

    private String winningTeamId;
    private Integer duration;
}
