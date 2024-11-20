package org.springbootapp.petdanauoblacima.service;

import lombok.RequiredArgsConstructor;
import org.springbootapp.petdanauoblacima.dto.PlayerDto;
import org.springbootapp.petdanauoblacima.model.Player;
import org.springbootapp.petdanauoblacima.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    /**
     * Retrieves all players from the database and maps them to PlayerDto.
     *
     * @return List of PlayerDto objects representing all players.
     */
    public List<PlayerDto> getAll() {
        return playerRepository.findAll().stream().map(PlayerDto::new).toList();
    }

    /**
     * Creates a new player with the given nickname.
     * Ensures the nickname is unique before saving the player.
     *
     * @param nickname The nickname of the player to be created.
     * @return The saved Player entity.
     * @throws IllegalArgumentException if the nickname already exists.
     */
    public Player createPlayer(String nickname) {
        // Check for duplicate nickname
        if (playerRepository.findByNickname(nickname).isPresent())
            throw new IllegalArgumentException("Nickname " + nickname + " is already taken");

        // Create and save the player
        Player player = new Player();
        player.setNickname(nickname);
        return playerRepository.save(player);
    }

    /**
     * Fetches a player by their unique ID and converts the result to PlayerDto.
     *
     * @param id The ID of the player to retrieve.
     * @return PlayerDto object representing the player.
     * @throws NoSuchElementException if the player is not found.
     */
    public PlayerDto getPlayerById(String id) {
        return new PlayerDto(playerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Player not found with ID: " + id)));
    }
}
