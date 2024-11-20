package org.springbootapp.petdanauoblacima.service;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springbootapp.petdanauoblacima.model.Player;
import org.springbootapp.petdanauoblacima.model.Team;
import org.springbootapp.petdanauoblacima.repository.PlayerRepository;
import org.springbootapp.petdanauoblacima.repository.TeamRepository;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class MatchServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private MatchService matchService;

    private Team team1, team2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        List<Player> team1Players = Arrays.asList(new Player(), new Player(), new Player(), new Player(), new Player());
        List<Player> team2Players = Arrays.asList(new Player(), new Player(), new Player(), new Player(), new Player());

        for (int i = 0; i < 5; i++) {
            team1Players.get(i).setNickname("Player" + (i + 1));
            team2Players.get(i).setNickname("Player" + (i + 6));
        }

        team1 = new Team();
        team1.setId("validId1");
        team1.setTeamName("team1");
        team1.setPlayers(team1Players);

        team2 = new Team();
        team2.setId("validId2");
        team2.setTeamName("team2");
        team2.setPlayers(team2Players);

        Mockito.when(teamRepository.findById(team1.getId())).thenReturn(Optional.of(team1));
        Mockito.when(teamRepository.findById(team2.getId())).thenReturn(Optional.of(team2));

        for (Player player : team1Players) Mockito.when(playerRepository.save(player)).thenReturn(player);
        for (Player player : team2Players) Mockito.when(playerRepository.save(player)).thenReturn(player);
    }

    @Test
    public void testCreateMatchWithInvalidDuration() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> matchService.addMatch(
                        "existentId1", "existentId2", "existentId2", 0));
        assertEquals("Duration must be at least 1 hour", exception.getMessage());
    }

    @Test
    public void testCreateMatchWithSameTeams() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> matchService.addMatch(
                        team1.getId(), team1.getId(), team1.getId(), 1));
        assertEquals("A match can't be played between the same team", exception.getMessage());
    }

    @Test
    public void testCreateMatchWithNonExistentTeam() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> matchService.addMatch(
                        team1.getId(), "nonExistentId", "nonExistentId", 2));
        assertEquals("Team with ID " + team1.getId() + " doesn't exist", exception.getMessage());
    }

    @Test
    public void testCreateMatchWithNonExistentTeams() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> matchService.addMatch(
                        "nonExistentId1", "nonExistentId2", "nonExistentId1", 2));
        assertEquals("Team with ID nonExistentId1 doesn't exist", exception.getMessage());
    }

    @Test
    public void testCreateMatchWithInvalidWinnersId() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> matchService.addMatch(
                        team1.getId(), team2.getId(), "randomId", 1));
        assertEquals("Winning team must be one of the two teams or null", exception.getMessage());
    }
}
