package org.springbootapp.petdanauoblacima.dto;

import lombok.Getter;
import org.springbootapp.petdanauoblacima.model.Team;

import java.util.List;

@Getter
public class TeamDto {

    private final String id;
    private final String teamName;
    private final List<PlayerDto> playerDtos;

    public TeamDto(Team team) {
        this.id = team.getId();
        this.teamName = team.getTeamName();
        this.playerDtos = team.getPlayers().stream().map(PlayerDto::new).toList();
    }
}
