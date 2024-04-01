package org.board_games_shop.repository;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.board_games_shop.entity.Order;

import java.util.List;

import static org.board_games_shop.entity.QCart.cart;
import static org.board_games_shop.entity.QCartGoods.cartGoods;
import static org.board_games_shop.entity.QOrder.order;
import static org.board_games_shop.entity.users.QUser.user;

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
