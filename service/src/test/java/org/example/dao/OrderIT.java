package org.example.dao;

import org.example.entity.*;
import org.example.entity.goods.BoardGames;
import org.example.entity.goods.Goods;
import org.example.entity.users.Customer;
import org.example.entity.users.User;
import org.example.nodeModel.AddressNode;
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

class OrderIT {
    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();
    private final Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
            new Class[]{Session.class}, (proxy, method, args1) -> method.invoke(SESSION_FACTORY.getCurrentSession(), args1));
    private OrderRepository orderRepository = null;

    @BeforeAll
    static void initDb() {
        TestDataImporter.importData(SESSION_FACTORY);
    }

    @BeforeEach
    void getTransaction() {
        session.beginTransaction();
        orderRepository = new OrderRepository(session);
    }

    @AfterEach
    void commitTransaction() {
        session.getTransaction().commit();
    }

    @Test
    void checkFindByIdOrder() {

        var order = orderRepository.findById(1L);

        assertTrue(order.isPresent());
        assertThat(order.get().getStatus()).isEqualTo(Status.PAID);
        assertThat(order.get().getCartGoods().getTotalPrice()).isEqualTo(200);
    }

    @Test
    void checkOrderCreation() {

        UserRepository userRepository = new UserRepository(session);
        CartRepository cartRepository = new CartRepository(session);
        GoodsRepository goodsRepository = new GoodsRepository(session);
        CartGoodsRepository cartGoodsRepository = new CartGoodsRepository(session);
        var user = getCustomerUser();
        var cart = getCart(user);
        var goods = getBoardGame();
        var cartGoods = getCartGoods(goods, cart);
        var order = getOrder(cartGoods, Status.PAID);

        userRepository.save(user);
        goodsRepository.save(goods);
        cartRepository.save(cart);
        cartGoodsRepository.save(cartGoods);
        var savedOrder = orderRepository.save(order);

        assertThat(orderRepository.findById(savedOrder.getId()).orElseThrow()).isEqualTo(order);

        orderRepository.delete(savedOrder.getId());
    }

    @Test
    void checkOrderUpdate() {

        UserRepository userRepository = new UserRepository(session);
        CartRepository cartRepository = new CartRepository(session);
        GoodsRepository goodsRepository = new GoodsRepository(session);
        CartGoodsRepository cartGoodsRepository = new CartGoodsRepository(session);
        var user = getCustomerUser();
        var cart = getCart(user);
        var goods = getBoardGame();
        var cartGoods = getCartGoods(goods, cart);
        var order = getOrder(cartGoods, Status.PAID);

        userRepository.save(user);
        goodsRepository.save(goods);
        cartRepository.save(cart);
        cartGoodsRepository.save(cartGoods);
        Order savedOrder = orderRepository.save(order);
        savedOrder.setStatus(Status.RESERVED);
        orderRepository.update(savedOrder);
        session.evict(savedOrder);

        assertThat(orderRepository.findById(savedOrder.getId()).orElseThrow().getStatus()).isEqualTo(Status.RESERVED);

        orderRepository.delete(savedOrder.getId());
    }

    @Test
    void checkOrderDelete() {

        UserRepository userRepository = new UserRepository(session);
        CartRepository cartRepository = new CartRepository(session);
        GoodsRepository goodsRepository = new GoodsRepository(session);
        CartGoodsRepository cartGoodsRepository = new CartGoodsRepository(session);
        var user = getCustomerUser();
        var cart = getCart(user);
        var goods = getBoardGame();
        var cartGoods = getCartGoods(goods, cart);
        var order = getOrder(cartGoods, Status.PAID);

        userRepository.save(user);
        goodsRepository.save(goods);
        cartRepository.save(cart);
        cartGoodsRepository.save(cartGoods);
        orderRepository.save(order);
        session.evict(order);
        orderRepository.delete(order.getId());

        assertNull(orderRepository.findById(order.getId()).orElse(null));
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

    @Test
    void checkAllProducersList() {

        List<Order> orders = orderRepository.findAll();

        assertThat(orders.size()).isEqualTo(4);
        assertThatList(orders.stream().map(Order::getStatus).toList())
                .containsExactly(Status.PAID, Status.PAID, Status.RESERVED, Status.RESERVED);
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

    private Order getOrder(CartGoods cartGoods, Status status) {
        return Order.builder()
                .cartGoods(cartGoods)
                .status(status)
                .build();
    }
}
