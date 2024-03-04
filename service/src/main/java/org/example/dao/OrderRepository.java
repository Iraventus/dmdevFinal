package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.entity.Order;

public class OrderRepository extends RepositoryBase<Long, Order> {
    public OrderRepository(EntityManager entityManager) {
        super(Order.class, entityManager);
    }
}
