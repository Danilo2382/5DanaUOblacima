package org.springbootapp.petdanauoblacima.dto;

import lombok.Getter;
import org.springbootapp.petdanauoblacima.model.Player;

@Getter
public class PlayerDto {

    private final String id;
    private final String nickname;
    private final Integer wins;
    private final Integer losses;
    private final Integer elo;
    private final Integer hoursPlayed;
    private String teamId = null;
    private final Integer ratingAdjustment;

    public PlayerDto(Player player) {
        this.id = player.getId();
        this.nickname = player.getNickname();
        this.wins = player.getWins();
        this.losses = player.getLosses();
        this.elo = player.getElo();
        this.hoursPlayed = player.getHoursPlayed();
        if (player.getTeam() != null) this.teamId = player.getTeam().getId();
        this.ratingAdjustment = player.getRatingAdjustment();
    }

}
