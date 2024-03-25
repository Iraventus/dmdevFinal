package org.boardGamesShop.repository;

import org.boardGamesShop.entity.users.Manager;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long>, FilterUserRepository {

    List<Manager> findAllBy(Sort sort);

    Optional<Manager> findByLogin(String login);
}
