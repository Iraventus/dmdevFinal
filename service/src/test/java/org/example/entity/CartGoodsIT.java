package org.example.entity;

import org.example.entity.goods.BoardGames;
import org.example.entity.users.Customer;
import org.example.entity.users.User;
import org.example.util.HibernateTestUtil;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.time.Instant;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CartGoodsIT {

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
    void checkCartGoodsCreation() {

        var user = getCustomerUser();
        var cart = getCart(user);
        var goods = getBoardGame();
        var cartGoods = CartGoods.builder()
                .goods(goods)
                .totalPrice(100)
                .cart(cart)
                .createdAt(Instant.now())
                .totalGoods(5)
                .build();

        session.persist(user);
        session.persist(goods);
        session.persist(cart);
        session.persist(cartGoods);
        session.clear();

        assertThat(session.get(CartGoods.class, cartGoods.getId()).getId()).isEqualTo(cartGoods.getId());
    }

    @Test
    void checkCartGoodsWithoutCartCreation() {

        var goods = getBoardGame();
        var cartGoods = CartGoods.builder()
                .goods(goods)
                .totalPrice(100)
                .build();

        session.persist(goods);
        session.evict(goods);

        assertThrows(PropertyValueException.class, () -> session.persist(cartGoods));
    }

    @Test
    void checkCartGoodsUpdate() {

        var user = getCustomerUser();
        var cart = getCart(user);
        var goods = getBoardGame();
        var cartGoods = CartGoods.builder()
                .totalPrice(100)
                .cart(cart)
                .build();

        session.persist(user);
        session.persist(cart);
        session.persist(goods);
        session.persist(cartGoods);
        session.clear();
        cartGoods.setTotalGoods(1);
        session.evict(cartGoods);
        session.merge(cartGoods);
        session.evict(cartGoods);

        assertThat(session.get(CartGoods.class, cartGoods.getId()).getTotalGoods()).isEqualTo(1);
    }

    @Test
    void checkCartGoodsDeletion() {

        var user = getCustomerUser();
        var cart = getCart(user);
        var goods = getBoardGame();
        var cartGoods = CartGoods.builder()
                .goods(goods)
                .totalPrice(100)
                .cart(cart)
                .build();

        session.persist(user);
        session.persist(cart);
        session.persist(goods);
        session.persist(cartGoods);
        session.evict(cartGoods);
        session.remove(cartGoods);

        assertNull(session.get(CartGoods.class, cartGoods.getId()));
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
                .build();
    }
}
