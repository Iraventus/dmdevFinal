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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CartGoodsIT {

    @Test
    void checkCartGoodsCreation() {
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

            session.beginTransaction();
            session.persist(cart);
            session.persist(goods);
            session.persist(cartGoods);

            assertThat(session.get(CartGoods.class, cartGoods.getId())).isEqualTo(cartGoods);
            session.getTransaction().commit();
        }
    }

    @Test
    void checkCartGoodsWithoutCartCreation() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            var goods = getBoardGame();
            var cartGoods = CartGoods.builder()
                    .goods(goods)
                    .totalPrice(100)
                    .build();

            session.beginTransaction();
            session.persist(goods);

            assertThrows(PropertyValueException.class, () -> session.persist(cartGoods));
            session.getTransaction().commit();
        }
    }

    @Test
    void checkCartGoodsUpdate() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            var cart = getCart();
            var goods = getBoardGame();
            var cartGoods = CartGoods.builder()
                    .totalPrice(100)
                    .cart(cart)
                    .build();

            session.beginTransaction();
            session.persist(cart);
            session.persist(goods);
            session.persist(cartGoods);
            cartGoods.setTotalGoods(1);
            session.evict(cartGoods);
            session.merge(cartGoods);

            assertThat(session.get(CartGoods.class, cartGoods.getId()).getTotalGoods()).isEqualTo(1);
            session.getTransaction().commit();
        }
    }

    @Test
    void checkCartGoodsDeletion() {
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

            session.beginTransaction();
            session.persist(cart);
            session.persist(goods);
            session.persist(cartGoods);
            session.evict(cartGoods);
            session.remove(cartGoods);

            assertNull(session.get(CartGoods.class, cartGoods.getId()));
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
