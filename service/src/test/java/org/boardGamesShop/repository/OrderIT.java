package org.boardGamesShop.repository;

import lombok.RequiredArgsConstructor;
import org.boardGamesShop.entity.*;
import org.boardGamesShop.entity.goods.BoardGames;
import org.boardGamesShop.entity.goods.Goods;
import org.boardGamesShop.entity.users.Customer;
import org.boardGamesShop.entity.users.User;
import org.boardGamesShop.nodeModel.AddressNode;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertNull;


@RequiredArgsConstructor
class OrderIT extends BaseIT {

    private final CartGoodsRepository cartGoodsRepository;
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final BoardGamesRepository boardGamesRepository;

    @Test
    void checkFindByIdOrder() {
        var user = getCustomerUser();
        var cart = getCart(user);
        var goods = getBoardGame();
        var cartGoods = getCartGoods(goods, cart);
        var initialOrder = getOrder(cartGoods, Status.PAID);
        customerRepository.save(user);
        boardGamesRepository.save(goods);
        cartRepository.save(cart);
        cartGoodsRepository.save(cartGoods);
        orderRepository.save(initialOrder);

        Order order = orderRepository.findById(initialOrder.getId()).orElseThrow();

        assertThat(order.getStatus()).isEqualTo(Status.PAID);
        assertThat(order.getCartGoods().getTotalPrice()).isEqualTo(100);
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
        entityManager.clear();

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
        entityManager.clear();
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
