package org.boardGamesShop.repository;

import org.boardGamesShop.entity.Producer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    Optional<Producer> findProducerByAccessoriesName(String name);

    List<Producer> findAllBy(Sort sort);
}
