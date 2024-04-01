package org.board_games_shop.repository;

import org.board_games_shop.entity.users.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long>, FilterUserRepository<Manager> {

    Optional<Manager> findByLogin(String login);
}
