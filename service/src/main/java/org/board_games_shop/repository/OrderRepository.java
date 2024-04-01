package org.board_games_shop.repository;

import org.board_games_shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, FilterOrderRepository {

}
