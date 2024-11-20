package org.springbootapp.petdanauoblacima.service;

import lombok.RequiredArgsConstructor;
import org.springbootapp.petdanauoblacima.model.Player;
import org.springbootapp.petdanauoblacima.model.Team;
import org.springbootapp.petdanauoblacima.repository.PlayerRepository;
import org.springbootapp.petdanauoblacima.util.EloCalculator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final PlayerRepository playerRepository;

    /**
     * Updates the statistics of all players in two teams after a match.
     *
     * @param team1         The first team in the match.
     * @param team2         The second team in the match.
     * @param winningTeamId The ID of the winning team, or null if it's a draw.
     * @param duration      The duration of the match in hours.
     */
    public void updatePlayersStats(Team team1, Team team2, String winningTeamId, int duration) {
        // Calculate average ElO for both teams
        int team1AvgElo = EloCalculator.calculateTeamAverageElo(team1);
        int team2AvgElo = EloCalculator.calculateTeamAverageElo(team2);

        // Updates stats for players in team1 than team2
        for (Player player : team1.getPlayers()) updatePlayerStats(player, team1AvgElo, winningTeamId, duration);
        for (Player player : team2.getPlayers()) updatePlayerStats(player, team2AvgElo, winningTeamId, duration);
    }

    /**
     * Updates the statistics of a single player after a match.
     *
     * @param player The player whose stats are being updated.
     * @param opposingTeamAvgElo The average ELO of the opposing team.
     * @param winningTeamId The ID of the winning team, or null if it's a draw.
     * @param duration The duration of the match in hours.
     */
    private void updatePlayerStats(Player player, int opposingTeamAvgElo, String winningTeamId, int duration) {
        // Update hours played
        player.setHoursPlayed(player.getHoursPlayed() + duration);

        // Update wins and losses based on the match outcome
        if (winningTeamId != null) {
            if (winningTeamId.equals(player.getTeam().getId())) player.setWins(player.getWins() + 1);
            else player.setLosses(player.getLosses() + 1);
        }

        // Update the player's ELO rating
        EloCalculator.updatePlayerElo(player, opposingTeamAvgElo, winningTeamId);

        // Save updated player stats
        playerRepository.save(player);
    }
}
