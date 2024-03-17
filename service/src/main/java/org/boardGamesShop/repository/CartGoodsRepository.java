package org.boardGamesShop.repository;

import jakarta.persistence.EntityManager;
import org.boardGamesShop.entity.CartGoods;
import org.springframework.stereotype.Repository;

@Repository
public class CartGoodsRepository extends RepositoryBase<Long, CartGoods> {

    public CartGoodsRepository(EntityManager entityManager) {
        super(CartGoods.class, entityManager);
    }
}
