package org.springbootapp.petdanauoblacima.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springbootapp.petdanauoblacima.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;

@DataJpaTest
public class PlayerServiceTest {

    @Autowired
    private PlayerRepository playerRepository;

    private PlayerService playerService;

    @BeforeEach
    public void setUp() {
        playerService = new PlayerService(playerRepository);
    }

    @Test
    public void testCreatePlayerWithDuplicateNickname() {
        playerService.createPlayer("Player1");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> playerService.createPlayer("Player1"));
        assertEquals("Nickname Player1 is already taken", exception.getMessage());
    }

    @Test
    public void testGetPlayerWithNonExistentId() {
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> playerService.getPlayerById("nonExistentId"));
        assertEquals("Player not found with ID: nonExistentId", exception.getMessage());
    }
}
