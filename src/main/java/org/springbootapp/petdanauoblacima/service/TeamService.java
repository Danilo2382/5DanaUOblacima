package org.springbootapp.petdanauoblacima.service;

import lombok.RequiredArgsConstructor;
import org.springbootapp.petdanauoblacima.dto.TeamDto;
import org.springbootapp.petdanauoblacima.model.Player;
import org.springbootapp.petdanauoblacima.model.Team;
import org.springbootapp.petdanauoblacima.repository.PlayerRepository;
import org.springbootapp.petdanauoblacima.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    /**
     * Creates a new team with the given name and player IDs.
     * Validates that the team name is unique, has exactly 5 players, and all players are valid.
     *
     * @param teamName   The name of the team.
     * @param playersIds List of player IDs to be added to the team.
     * @return TeamDto object representing the created team.
     * @throws IllegalArgumentException if validation fails (e.g., duplicate name or invalid player count).
     * @throws NoSuchElementException   if a player is not found.
     * @throws IllegalStateException    if a player is already in another team.
     */
    public TeamDto createTeam(String teamName, List<String> playersIds) {
        // Validate the team name, players IDs, and ensure all are unique
        validateTeamDetails(teamName, playersIds);

        // Create the team entity
        Team team = new Team();
        team.setTeamName(teamName);

        // Fetch and validate players
        List<Player> players = new ArrayList<>();
        for (String playerId : playersIds) {
            Player player = playerRepository.findById(playerId)
                    .orElseThrow(() -> new NoSuchElementException("Player not found with ID: " + playerId));

            if (player.getTeam() != null)
                throw new IllegalStateException("Player is already in a team: " + player.getTeam().getTeamName());

            // Set the team for the player and add it to the team
            player.setTeam(team);
            players.add(player);
        }

        // Save the team by its players and return a DTO representation
        team.setPlayers(players);
        return new TeamDto(teamRepository.save(team));
    }

    /**
     * Fetches a team by its unique ID and converts it to TeamDto.
     *
     * @param teamId The ID of the team to fetch.
     * @return TeamDto object representing the fetched team.
     * @throws NoSuchElementException if the team is not found.
     */
    public TeamDto getTeamById(String teamId) {
        return new TeamDto(teamRepository.findById(teamId)
                .orElseThrow(() -> new NoSuchElementException("Team not found with ID: " + teamId)));
    }


    /**
     * Validates the team details, including name uniqueness and player count.
     *
     * @param teamName   The name of the team to be validated.
     * @param playersIds List of player IDs to be validated.
     * @throws IllegalArgumentException if any validation rule is violated.
     */
    private void validateTeamDetails(String teamName, List<String> playersIds) {
        // Check if team name is already in use
        if (teamRepository.findByTeamName(teamName).isPresent())
            throw new IllegalArgumentException("Team name " + teamName + " is already taken");

        // Ensure the team has exactly 5 players
        if (playersIds.size() != 5)
            throw new IllegalArgumentException("Team must have exactly 5 players");

        // Ensure all players IDs are unique
        if (new HashSet<>(playersIds).size() != playersIds.size())
            throw new IllegalArgumentException("Players IDs must be unique");
    }
}
