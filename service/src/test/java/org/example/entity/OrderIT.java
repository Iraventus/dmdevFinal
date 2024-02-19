package org.example.entity;

import org.example.entity.goods.BoardGames;
import org.example.entity.users.Customer;
import org.example.nodeModel.AddressNode;
import org.example.util.HibernateTestUtil;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderIT {

    @Test
    void checkOrderCreation() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            var cart = getCart();
            var goods = getBoardGame();
            var cartGoods = CartGoods.builder()
                    .goods(goods)
                    .totalPrice(100)
                    .cart(cart)
                    .build();

            var order = Order.builder()
                    .status(Status.PAID)
                    .build();

            session.beginTransaction();
            session.persist(cart);
            session.persist(goods);
            session.persist(cartGoods);
            order.setCartGoods(cartGoods);
            session.persist(order);

            assertThat(session.get(Order.class, order.getId())).isEqualTo(order);
            session.getTransaction().commit();
        }
    }

    @Test
    void checkOrderWithoutCartCreation() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            var order = Order.builder()
                    .status(Status.PAID)
                    .build();

            session.beginTransaction();

            assertThrows(PropertyValueException.class, () -> session.persist(order));
            session.getTransaction().commit();
        }
    }

    @Test
    void checkOrderUpdate() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            var cart = getCart();
            var goods = getBoardGame();
            var cartGoods = CartGoods.builder()
                    .goods(goods)
                    .totalPrice(100)
                    .cart(cart)
                    .build();
            var order = Order.builder()
                    .status(Status.PAID)
                    .cartGoods(cartGoods)
                    .build();

            session.beginTransaction();
            session.persist(cart);
            session.persist(goods);
            session.persist(cartGoods);
            session.persist(order);
            order.setStatus(Status.RESERVED);
            session.evict(order);
            session.merge(order);

            assertThat(session.get(Order.class, order.getId()).getStatus()).isEqualTo(Status.RESERVED);
            session.getTransaction().commit();
        }
    }

    private BoardGames getBoardGame() {
        return BoardGames.builder()
                .name("someName")
                .localization(Localization.FR)
                .quantity(1)
                .boardGameTheme(BoardGameTheme.COOP)
                .build();
    }

    private Cart getCart() {
        return Cart.builder()
                .name("cart")
                .user(getCustomerUser())
                .build();
    }

    private Customer getCustomerUser() {
        return Customer.builder()
                .login("Ivan@gmail1.com")
                .password("12345")
                .firstname("Ivan")
                .lastname("Ivanov")
                .birthDate(LocalDate.of(2000, 1, 19))
                .address(new AddressNode()
                        .getAddressConvertedToJsonNode("someCountry", "someCity",
                                "someStreetName", 1, 1))
                .build();
    }
}
