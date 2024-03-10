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
import org.example.entity.Order;
import org.example.entity.Status;
import org.example.entity.goods.BoardGames;
import org.example.entity.goods.Goods;
import org.example.entity.users.Customer;
import org.example.entity.users.User;
import org.example.nodeModel.AddressNode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class OrderIT {

  private final static AnnotationConfigApplicationContext context
      = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
  private static CartGoodsRepository cartGoodsRepository;
  private static CartRepository cartRepository;
  private static CustomerRepository customerRepository;
  private static OrderRepository orderRepository;
  private static BoardGamesRepository boardGamesRepository;
  private static EntityManager session;

  @BeforeAll
  static void dataPreparation() {
    customerRepository = context.getBean(CustomerRepository.class);
    cartRepository = context.getBean(CartRepository.class);
    cartGoodsRepository = context.getBean(CartGoodsRepository.class);
    orderRepository = context.getBean(OrderRepository.class);
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
  void checkFindByIdOrder() {

    var order = orderRepository.findById(1L);

    assertTrue(order.isPresent());
    assertThat(order.get().getStatus()).isEqualTo(Status.PAID);
    assertThat(order.get().getCartGoods().getTotalPrice()).isEqualTo(200);
  }

  @Test
  void checkOrderCreation() {

    var user = getCustomerUser();
    var cart = getCart(user);
    var goods = getBoardGame();
    var cartGoods = getCartGoods(goods, cart);
    var order = getOrder(cartGoods, Status.PAID);

    customerRepository.save(user);
    boardGamesRepository.save(goods);
    cartRepository.save(cart);
    cartGoodsRepository.save(cartGoods);
    var savedOrder = orderRepository.save(order);

    assertThat(orderRepository.findById(savedOrder.getId()).orElseThrow()).isEqualTo(order);

    orderRepository.delete(savedOrder);
  }

  @Test
  void checkOrderUpdate() {

    var user = getCustomerUser();
    var cart = getCart(user);
    var goods = getBoardGame();
    var cartGoods = getCartGoods(goods, cart);
    var order = getOrder(cartGoods, Status.PAID);

    customerRepository.save(user);
    boardGamesRepository.save(goods);
    cartRepository.save(cart);
    cartGoodsRepository.save(cartGoods);
    Order savedOrder = orderRepository.save(order);
    savedOrder.setStatus(Status.RESERVED);
    orderRepository.update(savedOrder);
    session.clear();

    assertThat(orderRepository.findById(savedOrder.getId()).orElseThrow().getStatus()).isEqualTo(
        Status.RESERVED);

    orderRepository.delete(savedOrder);
  }

  @Test
  void checkOrderDelete() {

    var user = getCustomerUser();
    var cart = getCart(user);
    var goods = getBoardGame();
    var cartGoods = getCartGoods(goods, cart);
    var order = getOrder(cartGoods, Status.PAID);

    customerRepository.save(user);
    boardGamesRepository.save(goods);
    cartRepository.save(cart);
    cartGoodsRepository.save(cartGoods);
    orderRepository.save(order);
    session.clear();
    orderRepository.delete(order);

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
