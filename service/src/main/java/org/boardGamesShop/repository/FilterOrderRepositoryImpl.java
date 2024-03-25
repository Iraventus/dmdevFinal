package org.boardGamesShop.repository;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.boardGamesShop.entity.Order;

import java.util.List;

import static org.boardGamesShop.entity.QCart.cart;
import static org.boardGamesShop.entity.QCartGoods.cartGoods;
import static org.boardGamesShop.entity.QOrder.order;
import static org.boardGamesShop.entity.users.QUser.user;

@RequiredArgsConstructor
public class FilterOrderRepositoryImpl implements FilterOrderRepository {

    private final EntityManager entityManager;

    @Override
    public List<Order> findAllByFilter(Long id) {
        return new JPAQuery<Order>(entityManager)
                .select(order)
                .from(order)
                .join(order.cartGoods, cartGoods)
                .join(cartGoods.cart, cart)
                .join(cart.user, user)
                .where(user.id.eq(id))
                .fetch();
    }
}
