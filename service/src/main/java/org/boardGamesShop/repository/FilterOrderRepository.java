package org.boardGamesShop.repository;

import org.boardGamesShop.entity.Order;

import java.util.List;

public interface FilterOrderRepository {

    List<Order> findAllByFilter(Long userId);
}
