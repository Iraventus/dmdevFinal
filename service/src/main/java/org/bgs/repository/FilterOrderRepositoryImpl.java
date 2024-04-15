package org.bgs.repository;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.bgs.entity.Order;

import java.util.List;

import static org.bgs.entity.QCart.cart;
import static org.bgs.entity.QCartGoods.cartGoods;
import static org.bgs.entity.QOrder.order;
import static org.bgs.entity.users.QCustomer.customer;

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
                .join(cart.user, customer)
                .where(customer.id.eq(id))
                .fetch();
    }
}
