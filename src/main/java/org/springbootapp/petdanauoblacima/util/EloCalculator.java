package org.springbootapp.petdanauoblacima.util;

import org.springbootapp.petdanauoblacima.model.Player;
import org.springbootapp.petdanauoblacima.model.Team;

public class EloCalculator {

    /**
     * Updates the ELO rating of a player after a match.
     *
     * @param player The player whose ELO rating is being updated.
     * @param opposingTeamAvgElo The average ELO rating of the opposing team.
     * @param winningTeamId The ID of the winning team, or null if the match is a draw.
     */
    public static void updatePlayerElo(Player player, int opposingTeamAvgElo, String winningTeamId) {
        int currentElo = player.getElo();

        // Calculate expected ELO outcome for the player
        double expectedElo = 1.0 / (1 + Math.pow(10, (opposingTeamAvgElo - currentElo) / 400.0));

        // Determine the actual match result for the player (0.5 default for a draw)
        double result = 0.5;
        if (winningTeamId != null) result = player.getTeam().getId().equals(winningTeamId) ? 1.0 : 0.0;

        // Fetch K-factor based on player's experience
        int k = getK(player);
        player.setRatingAdjustment(k);

        // Calculate new ElO rating and updating it for player
        int newElo = (int) (currentElo + k * (result - expectedElo));
        player.setElo(newElo);
    }

    /**
     * Calculates the average ELO of all players in a team.
     *
     * @param team The team whose average ELO is being calculated.
     * @return The average ELO rating of the team.
     */
    public static int calculateTeamAverageElo(Team team) {
        int totalElo = team.getPlayers().stream().mapToInt(Player::getElo).sum();
        return totalElo / team.getPlayers().size();
    }

    /**
     * Determines the K-factor based on the number of hours a player has played.
     *
     * @param player The player whose K-factor is being determined.
     * @return The K-factor for the player.
     */
    private static int getK(Player player) {
        int hoursPlayed = player.getHoursPlayed();
        if (hoursPlayed < 500) return 50;
        else if (hoursPlayed < 1000) return 40;
        else if (hoursPlayed < 3000) return 30;
        else if (hoursPlayed < 5000) return 20;
        else return 10;
    }
}
