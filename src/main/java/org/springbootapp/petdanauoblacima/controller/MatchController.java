package org.springbootapp.petdanauoblacima.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springbootapp.petdanauoblacima.request.MatchRequest;
import org.springbootapp.petdanauoblacima.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping
    public ResponseEntity<?> createMatch(@Valid @RequestBody MatchRequest request) {
        matchService.addMatch(
                request.getTeam1Id(),
                request.getTeam2Id(),
                request.getWinningTeamId(),
                request.getDuration());
        return ResponseEntity.ok().build();
    }
}
