package org.example.entity;

import org.example.entity.goods.BoardGames;
import org.example.entity.users.Customer;
import org.example.entity.users.User;
import org.example.nodeModel.AddressNode;
import org.example.util.HibernateTestUtil;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderIT {
    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private Session session;

    @BeforeEach
    void getTransaction() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void commitTransaction() {
        session.getTransaction().commit();
    }

    @AfterAll
    void finish() {
        sessionFactory.close();
    }

    @Test
    void checkOrderCreation() {

        var user = getCustomerUser();
        var cart = getCart(user);
        var goods = getBoardGame();
        var cartGoods = CartGoods.builder()
                .goods(goods)
                .totalPrice(100)
                .cart(cart)
                .build();

        var order = Order.builder()
                .status(Status.PAID)
                .build();

        session.persist(user);
        session.persist(cart);
        session.persist(goods);
        session.persist(cartGoods);
        order.setCartGoods(cartGoods);
        session.persist(order);
        session.clear();

        assertThat(session.get(Order.class, order.getId()).getId()).isEqualTo(order.getId());
    }

    @Test
    void checkOrderWithoutCartCreation() {

        var order = Order.builder()
                .status(Status.PAID)
                .build();

        assertThrows(PropertyValueException.class, () -> session.persist(order));
    }

    @Test
    void checkOrderUpdate() {

        var user = getCustomerUser();
        var cart = getCart(user);
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

        session.persist(user);
        session.persist(cart);
        session.persist(goods);
        session.persist(cartGoods);
        session.persist(order);
        order.setStatus(Status.RESERVED);
        session.evict(order);
        session.merge(order);

        assertThat(session.get(Order.class, order.getId()).getStatus()).isEqualTo(Status.RESERVED);
    }

    private BoardGames getBoardGame() {
        return BoardGames.builder()
                .name("someName")
                .localization(Localization.FR)
                .quantity(1)
                .boardGameTheme(BoardGameTheme.COOP)
                .build();
    }

    private Cart getCart(User user) {
        return Cart.builder()
                .name("cart")
                .user(user)
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
