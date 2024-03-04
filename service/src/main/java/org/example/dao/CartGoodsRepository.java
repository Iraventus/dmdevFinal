package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.entity.CartGoods;

public class CartGoodsRepository extends RepositoryBase<Long, CartGoods> {
    public CartGoodsRepository(EntityManager entityManager) {
        super(CartGoods.class, entityManager);
    }
}
