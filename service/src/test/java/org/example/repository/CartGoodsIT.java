package org.example.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.example.util.DataImporterUtil.dataInit;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.example.config.ApplicationConfiguration;
import org.example.entity.BoardGameTheme;
import org.example.entity.Cart;
import org.example.entity.CartGoods;
import org.example.entity.Localization;
import org.example.entity.goods.BoardGames;
import org.example.entity.goods.Goods;
import org.example.entity.users.Customer;
import org.example.entity.users.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class CartGoodsIT {
    private final static AnnotationConfigApplicationContext context
            = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
    private static CartGoodsRepository cartGoodsRepository;
    private static BoardGamesRepository boardGamesRepository;
    private static CartRepository cartRepository;
    private static CustomerRepository customerRepository;
    private static EntityManager session;

    @BeforeAll
    static void dataPreparation() {
        customerRepository = context.getBean(CustomerRepository.class);
        cartRepository = context.getBean(CartRepository.class);
        cartGoodsRepository = context.getBean(CartGoodsRepository.class);
        boardGamesRepository = context.getBean(BoardGamesRepository.class);
        session = context.getBean(EntityManager.class);
        dataInit(context);
    }

    @AfterAll
    static void closeContext() {
        context.close();
    }

    @BeforeEach
    void getTransaction() {
        session.getTransaction().begin();
    }

    @AfterEach
    void rollbackTransaction() {
        session.getTransaction().rollback();
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

        var user = getCustomerUser();
        var cart = getCart(user);
        var goods = getBoardGame();
        var cartGoods = getCartGoods(goods, cart);

        customerRepository.save(user);
        boardGamesRepository.save(goods);
        cartRepository.save(cart);
        var savedCartGoods = cartGoodsRepository.save(cartGoods);
        session.clear();

        assertThat(cartGoodsRepository.findById(savedCartGoods.getId()).orElseThrow()).isEqualTo(cartGoods);

        cartGoodsRepository.delete(savedCartGoods);
    }

    @Test
    void checkCartGoodsUpdate() {

        var user = getCustomerUser();
        var cart = getCart(user);
        var goods = getBoardGame();
        var cartGoods = getCartGoods(goods, cart);

        customerRepository.save(user);
        boardGamesRepository.save(goods);
        cartRepository.save(cart);
        var savedCartGoods = cartGoodsRepository.save(cartGoods);
        session.clear();
        savedCartGoods.setTotalGoods(10);
        cartGoodsRepository.update(savedCartGoods);
        session.clear();

        assertThat(cartGoodsRepository.findById(savedCartGoods.getId()).orElseThrow().getTotalGoods()).isEqualTo(10);

        cartGoodsRepository.delete(savedCartGoods);
    }

    @Test
    void checkCartGoodsDeletion() {

        var user = getCustomerUser();
        var cart = getCart(user);
        var goods = getBoardGame();
        var cartGoods = getCartGoods(goods, cart);

        customerRepository.save(user);
        boardGamesRepository.save(goods);
        cartRepository.save(cart);
        var savedCartGoods = cartGoodsRepository.save(cartGoods);
        session.clear();
        cartGoodsRepository.delete(savedCartGoods);

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
