package org.springbootapp.petdanauoblacima.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springbootapp.petdanauoblacima.repository.PlayerRepository;
import org.springbootapp.petdanauoblacima.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@DataJpaTest
public class TeamServiceTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private TeamService teamService;
    private final ArrayList<String> playerIds = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        teamService = new TeamService(teamRepository, playerRepository);
        PlayerService playerService = new PlayerService(playerRepository);
        for (int i = 1; i <= 5; i++) playerIds.add(playerService.createPlayer("Player" + i).getId());
    }

    @Test
    public void testTeamWithMoreThanFivePlayers() {
        playerIds.add("g");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> teamService.createTeam("team1", playerIds));
        assertEquals("Team must have exactly 5 players", exception.getMessage());
    }

    @Test
    public void testTeamWithLessThanFivePlayers() {
        playerIds.remove(playerIds.size() - 1);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> teamService.createTeam("team1", playerIds));
        assertEquals("Team must have exactly 5 players", exception.getMessage());
    }

    @Test
    public void testCreateTeamWithDuplicateTeamName() {
        teamService.createTeam("team1", playerIds);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> teamService.createTeam("team1", playerIds));
        assertEquals("Team name team1 is already taken", exception.getMessage());
    }

    @Test
    public void testCreateTeamWithDuplicatePlayerId() {
        String firstId = playerIds.get(0);
        playerIds.set(1, firstId);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> teamService.createTeam("team1", playerIds));
        assertEquals("Players IDs must be unique", exception.getMessage());
    }

    @Test
    public void testCreateTeamWithNonExistentPlayer() {
        playerIds.set(3, "nonExistentId");
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> teamService.createTeam("team1", playerIds));
        assertEquals("Player not found with ID: nonExistentId", exception.getMessage());
    }
}
