package org.bgs.repository;

import org.bgs.entity.goods.Accessories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccessoriesRepository extends JpaRepository<Accessories, Long> {

    Optional<Accessories> findByName(String name);

    List<Accessories> findAllByProducerName(String name);

    List<Accessories> findAllByNameContains(String name);
}
