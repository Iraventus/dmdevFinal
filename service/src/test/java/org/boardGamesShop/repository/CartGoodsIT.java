package org.boardGamesShop.repository;

import lombok.RequiredArgsConstructor;
import org.boardGamesShop.entity.BoardGameTheme;
import org.boardGamesShop.entity.Localization;
import org.boardGamesShop.entity.goods.BoardGames;
import org.boardGamesShop.entity.Cart;
import org.boardGamesShop.entity.CartGoods;
import org.boardGamesShop.entity.goods.Goods;
import org.boardGamesShop.entity.users.Customer;
import org.boardGamesShop.entity.users.User;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertNull;

@RequiredArgsConstructor
class CartGoodsIT extends BaseIT {

    private final CartGoodsRepository cartGoodsRepository;
    private final BoardGamesRepository boardGamesRepository;
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;

    @Test
    void checkFindByIdCart() {
        var user = getCustomerUser();
        var cart = getCart(user);
        var goods = getBoardGame();
        var cartGoods = getCartGoods(goods, cart);

        customerRepository.save(user);
        boardGamesRepository.save(goods);
        cartRepository.save(cart);
        cartGoodsRepository.save(cartGoods);
        var savedCartGoods = cartGoodsRepository.findById(cartGoods.getId()).orElseThrow();

        assertThat(savedCartGoods.getTotalPrice()).isEqualTo(100);
        assertThat(savedCartGoods.getCart().getName()).isEqualTo("cart");
        assertThat(savedCartGoods.getGoods().getName()).isEqualTo("someName");
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
        entityManager.clear();

        assertThat(cartGoodsRepository.findById(savedCartGoods.getId()).orElseThrow()).isEqualTo(cartGoods);
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
        entityManager.clear();
        savedCartGoods.setTotalGoods(10);
        cartGoodsRepository.update(savedCartGoods);
        entityManager.clear();

        assertThat(cartGoodsRepository.findById(savedCartGoods.getId()).orElseThrow().getTotalGoods()).isEqualTo(10);
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
        entityManager.clear();
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
