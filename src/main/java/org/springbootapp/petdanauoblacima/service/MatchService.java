package org.springbootapp.petdanauoblacima.service;

import lombok.RequiredArgsConstructor;
import org.springbootapp.petdanauoblacima.model.Match;
import org.springbootapp.petdanauoblacima.model.Team;
import org.springbootapp.petdanauoblacima.repository.MatchRepository;
import org.springbootapp.petdanauoblacima.repository.TeamRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final StatsService statsService;

    /**
     * Adds a new match between two teams and updates player stats.
     *
     * @param team1Id       The ID of the first team.
     * @param team2Id       The ID of the second team.
     * @param winningTeamId The ID of the winning team, or null for a draw.
     * @param duration      The duration of the match in hours.
     * @throws IllegalArgumentException if validation fails.
     */
    public void addMatch(String team1Id, String team2Id, String winningTeamId, int duration) {
        // Validate input
        validateMatchInput(team1Id, team2Id, winningTeamId, duration);

        // Retrieve teams
        Team team1 = teamRepository.findById(team1Id)
                .orElseThrow(() -> new IllegalArgumentException("Team with ID " + team1Id + " doesn't exist"));
        Team team2 = teamRepository.findById(team2Id)
                .orElseThrow(() -> new IllegalArgumentException("Team with ID " + team2Id + " doesn't exist"));

        // Create and save match
        Match match = new Match(null, team1, team2, winningTeamId, duration);
        matchRepository.save(match);

        // Update players stats
        statsService.updatePlayersStats(team1, team2, winningTeamId, duration);
    }

    /**
     * Validates match input parameters.
     *
     * @param team1Id       The ID of the first team.
     * @param team2Id       The ID of the second team.
     * @param winningTeamId The ID of the winning team, or null for a draw.
     * @param duration      The duration of the match in hours.
     * @throws IllegalArgumentException if any validation rule is violated.
     */
    private void validateMatchInput(String team1Id, String team2Id, String winningTeamId, int duration) {
        // Check if the duration is at least one hour
        if (duration < 1)
            throw new IllegalArgumentException("Duration must be at least 1 hour");

        // Check if both teams are the same, which is not possible
        if (team1Id.equals(team2Id))
            throw new IllegalArgumentException("A match can't be played between the same team");

        // Check if the winning team is one of the two teams in the match
        if (winningTeamId != null && !winningTeamId.equals(team1Id) && !winningTeamId.equals(team2Id))
            throw new IllegalArgumentException("Winning team must be one of the two teams or null");
    }
}
