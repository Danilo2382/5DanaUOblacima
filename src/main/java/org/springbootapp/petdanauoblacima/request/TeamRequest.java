package org.springbootapp.petdanauoblacima.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class TeamRequest {

    @NotBlank(message = "Team name can't be null or empty")
    private String teamName;

    @NotNull(message = "Players list can't be null")
    @Size(min = 5, max = 5, message = "Players list must contain exactly 5 players")
    private List<String> players;

}
