package org.board_games_shop.repository;

import org.board_games_shop.entity.Order;

import java.util.List;

public interface FilterOrderRepository {

    List<Order> findAllByFilter(Long userId);
}
