package org.bgs.repository;

import org.bgs.entity.users.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long>, FilterManagerRepository{

    Optional<Manager> findByLogin(String login);
}
