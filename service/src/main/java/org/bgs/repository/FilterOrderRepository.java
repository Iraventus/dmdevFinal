package org.bgs.repository;

import org.bgs.entity.Order;

import java.util.List;

public interface FilterOrderRepository {

    List<Order> findAllByFilter(Long userId);
}
