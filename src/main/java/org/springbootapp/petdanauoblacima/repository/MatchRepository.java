package org.springbootapp.petdanauoblacima.repository;

import org.springbootapp.petdanauoblacima.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, String> {
}
