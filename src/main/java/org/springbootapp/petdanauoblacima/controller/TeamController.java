package org.springbootapp.petdanauoblacima.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springbootapp.petdanauoblacima.dto.TeamDto;
import org.springbootapp.petdanauoblacima.request.TeamRequest;
import org.springbootapp.petdanauoblacima.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(@Valid @RequestBody TeamRequest request) {
        return ResponseEntity.ok(teamService.createTeam(request.getTeamName(), request.getPlayers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable String id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }
}
