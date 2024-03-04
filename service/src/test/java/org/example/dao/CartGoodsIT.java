package org.example.dao;

import org.example.entity.BoardGameTheme;
import org.example.entity.Cart;
import org.example.entity.CartGoods;
import org.example.entity.Localization;
import org.example.entity.goods.BoardGames;
import org.example.entity.goods.Goods;
import org.example.entity.users.Customer;
import org.example.entity.users.User;
import org.example.util.HibernateTestUtil;
import org.example.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CartGoodsIT {

    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();
    private final Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
            new Class[]{Session.class}, (proxy, method, args1) -> method.invoke(SESSION_FACTORY.getCurrentSession(), args1));
    private CartGoodsRepository cartGoodsRepository = null;

    @BeforeAll
    static void initDb() {
        TestDataImporter.importData(SESSION_FACTORY);
    }

    @BeforeEach
    void getTransaction() {
        session.beginTransaction();
        cartGoodsRepository = new CartGoodsRepository(session);
    }

    @AfterEach
    void commitTransaction() {
        session.getTransaction().commit();
    }

    @Test
    void checkFindByIdCart() {

        var cartGoods = cartGoodsRepository.findById(1L);

        assertTrue(cartGoods.isPresent());
        assertThat(cartGoods.get().getTotalPrice()).isEqualTo(200);
        assertThat(cartGoods.get().getCart().getName()).isEqualTo("first Nick Cart");
        assertThat(cartGoods.get().getGoods().getName()).isEqualTo("UltraPro Sleeves");
    }

    @Test
    void checkCartGoodsCreation() {

        UserRepository userRepository = new UserRepository(session);
        CartRepository cartRepository = new CartRepository(session);
        GoodsRepository goodsRepository = new GoodsRepository(session);

        var user = getCustomerUser();
        var cart = getCart(user);
        var goods = getBoardGame();
        var cartGoods = getCartGoods(goods, cart);

        userRepository.save(user);
        goodsRepository.save(goods);
        cartRepository.save(cart);
        var savedCartGoods = cartGoodsRepository.save(cartGoods);
        session.clear();

        assertThat(cartGoodsRepository.findById(savedCartGoods.getId()).orElseThrow()).isEqualTo(cartGoods);

        cartGoodsRepository.delete(savedCartGoods.getId());
    }

    @Test
    void checkCartGoodsUpdate() {

        UserRepository userRepository = new UserRepository(session);
        CartRepository cartRepository = new CartRepository(session);
        GoodsRepository goodsRepository = new GoodsRepository(session);

        var user = getCustomerUser();
        var cart = getCart(user);
        var goods = getBoardGame();
        var cartGoods = getCartGoods(goods, cart);

        userRepository.save(user);
        goodsRepository.save(goods);
        cartRepository.save(cart);
        var savedCartGoods = cartGoodsRepository.save(cartGoods);
        session.clear();
        savedCartGoods.setTotalGoods(10);
        cartGoodsRepository.update(savedCartGoods);
        session.evict(savedCartGoods);

        assertThat(cartGoodsRepository.findById(savedCartGoods.getId()).orElseThrow().getTotalGoods()).isEqualTo(10);

        cartGoodsRepository.delete(savedCartGoods.getId());
    }

    @Test
    void checkCartGoodsDeletion() {

        UserRepository userRepository = new UserRepository(session);
        CartRepository cartRepository = new CartRepository(session);
        GoodsRepository goodsRepository = new GoodsRepository(session);
        var user = getCustomerUser();
        var cart = getCart(user);
        var goods = getBoardGame();
        var cartGoods = getCartGoods(goods, cart);

        userRepository.save(user);
        goodsRepository.save(goods);
        cartRepository.save(cart);
        var savedCartGoods = cartGoodsRepository.save(cartGoods);
        session.clear();
        cartGoodsRepository.delete(savedCartGoods.getId());

        assertNull(cartGoodsRepository.findById(savedCartGoods.getId()).orElse(null));
    }

    @Test
    void checkAllProducersList() {

        List<CartGoods> cartGoods = cartGoodsRepository.findAll();

        assertThat(cartGoods.size()).isEqualTo(4);
        assertThatList(cartGoods.stream().map(CartGoods::getTotalPrice).toList()).contains(200, 5000, 10000, 15000);
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

    private CartGoods getCartGoods(Goods goods, Cart cart) {
        return CartGoods.builder()
                .goods(goods)
                .totalPrice(100)
                .cart(cart)
                .createdAt(Instant.now())
                .totalGoods(5)
                .build();
    }
}
