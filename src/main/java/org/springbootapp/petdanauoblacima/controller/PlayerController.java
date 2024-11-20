package org.springbootapp.petdanauoblacima.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springbootapp.petdanauoblacima.dto.PlayerDto;
import org.springbootapp.petdanauoblacima.model.Player;
import org.springbootapp.petdanauoblacima.request.PlayerRequest;
import org.springbootapp.petdanauoblacima.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping("/create")
    public ResponseEntity<Player> createPlayer(@Valid @RequestBody PlayerRequest request) {
        return ResponseEntity.ok(playerService.createPlayer(request.getNickname()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable String id) {
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

    @GetMapping
    public ResponseEntity<List<PlayerDto>> getAll() {
        return ResponseEntity.ok(playerService.getAll());
    }
}
