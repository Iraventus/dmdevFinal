package org.boardGamesShop.repository;

import jakarta.persistence.EntityManager;
import org.boardGamesShop.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository extends RepositoryBase<Long, Order> {

    public OrderRepository(EntityManager entityManager) {
        super(Order.class, entityManager);
    }
}
