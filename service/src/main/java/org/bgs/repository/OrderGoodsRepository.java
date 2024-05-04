package org.bgs.repository;

import org.bgs.entity.OrderGoods;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderGoodsRepository extends JpaRepository<OrderGoods, Long> {

    @EntityGraph(attributePaths = {"goods"})
    List<OrderGoods> findAllByOrderId(Long id);
}
