package org.boardGamesShop.repository;

import org.boardGamesShop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, FilterOrderRepository {

}
