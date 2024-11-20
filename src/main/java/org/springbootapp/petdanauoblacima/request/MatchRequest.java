package org.springbootapp.petdanauoblacima.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MatchRequest {

    @NotBlank(message = "Team 1 ID cannot be null or empty")
    private String team1Id;

    @NotBlank(message = "Team 2 ID cannot be null or empty")
    private String team2Id;

    private String winningTeamId;

    @NotNull(message = "Duration can't be null")
    @Positive(message = "Duration must be greater than 0")
    private Integer duration;


}
