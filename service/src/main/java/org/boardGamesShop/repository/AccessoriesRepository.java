package org.boardGamesShop.repository;

import org.boardGamesShop.entity.goods.Accessories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccessoriesRepository extends JpaRepository<Accessories, Long> {

    Optional<Accessories> findByName(String name);

    Page<Accessories> findAllBy(Pageable pageable);

    List<Accessories> findAllByProducerName(String name);

    List<Accessories> findAllByNameContains(String name);
}
