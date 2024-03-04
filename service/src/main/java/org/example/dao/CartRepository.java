package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.entity.Cart;

public class CartRepository extends RepositoryBase<Long, Cart> {
    public CartRepository(EntityManager entityManager) {
        super(Cart.class, entityManager);
    }
}
