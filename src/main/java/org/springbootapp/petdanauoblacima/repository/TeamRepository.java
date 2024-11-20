package org.springbootapp.petdanauoblacima.repository;

import org.springbootapp.petdanauoblacima.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, String> {

    Optional<Team> findByTeamName(String teamName);
}
