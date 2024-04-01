package org.board_games_shop.repository;

import org.board_games_shop.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    Optional<Producer> findProducerByAccessoriesName(String name);
}
