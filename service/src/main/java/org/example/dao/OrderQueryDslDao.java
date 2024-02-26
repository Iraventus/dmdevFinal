package org.example.dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.dto.OrderFilter;
import org.example.entity.CartGoods;
import org.example.entity.Order;
import org.example.entity.goods.Goods;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;

import java.util.List;

import static org.example.entity.QCart.cart;
import static org.example.entity.QCartGoods.cartGoods;
import static org.example.entity.QOrder.order;
import static org.example.entity.users.QUser.user;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderQueryDslDao {

    private static final OrderQueryDslDao INSTANCE = new OrderQueryDslDao();

    public static OrderQueryDslDao getInstance() {
        return INSTANCE;
    }

    /**
     * Вернуть все заказы по user login
     */
    public List<Order> findALLOrdersByUserLogin(Session session, OrderFilter orderFilter) {

        var cartGoodsGraph = session.createEntityGraph(CartGoods.class);
        cartGoodsGraph.addAttributeNodes("cart", "goods");
        var goodsGraph = cartGoodsGraph.addSubgraph("goods", Goods.class);
        goodsGraph.addAttributeNodes("cartGoods");

        var predicate = QPredicate.builder()
                .add(orderFilter.getLogin(), user.login::eq)
                .add(orderFilter.getFirstName(), user.firstname::eq)
                .buildAnd();
        return new JPAQuery<Order>(session)
                .select(order)
                .from(order)
                .join(order.cartGoods, cartGoods)
                .join(cartGoods.cart, cart)
                .join(cart.user, user)
                .where(predicate)
                .setHint(GraphSemantic.LOAD.getJakartaHintName(), cartGoodsGraph)
                .fetch();
    }
}
