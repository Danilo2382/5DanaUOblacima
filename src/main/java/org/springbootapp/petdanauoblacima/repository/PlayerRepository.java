package org.springbootapp.petdanauoblacima.repository;

import org.springbootapp.petdanauoblacima.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, String> {

    Optional<Player> findByNickname(String nickname);
}
