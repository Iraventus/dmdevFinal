package org.example.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.CartGoods_;
import org.example.entity.Cart_;
import org.example.entity.Order;
import org.example.entity.Order_;
import org.example.entity.users.User_;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCriteriaDao {

    private static final OrderCriteriaDao INSTANCE = new OrderCriteriaDao();

    public static OrderCriteriaDao getInstance() {
        return INSTANCE;
    }

    /**
     * Вернуть все заказы по user login
     */
    public List<Order> findALLOrdersByUserLogin(Session session, String login) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Order.class);
        var order = criteria.from(Order.class);
        var cartGoods = order.join(Order_.CART_GOODS);
        var cart = cartGoods.join(CartGoods_.CART);
        var user = cart.join(Cart_.USER);
        criteria.select(order).where(cb.equal(user.get(User_.LOGIN), login));

        return session.createQuery(criteria).list();
    }
}
