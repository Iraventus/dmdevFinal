package org.boardGamesShop.repository;

import lombok.RequiredArgsConstructor;
import org.boardGamesShop.entity.Cart;
import org.boardGamesShop.entity.users.Customer;
import org.boardGamesShop.entity.users.User;
import org.boardGamesShop.nodeModel.AddressNode;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertNull;

@RequiredArgsConstructor
class CartIT extends BaseIT {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;

    @Test
    void checkFindByIdCart() {
        var user = getCustomerUser();
        var cart = getCart(user);

        customerRepository.save(user);
        cartRepository.save(cart);
        var savedCart = cartRepository.findById(cart.getId()).orElseThrow();

        assertThat(savedCart.getName()).isEqualTo("myCart");
    }

    @Test
    void checkCartCreation() {
        var user = getCustomerUser();
        var cart = getCart(user);

        customerRepository.save(user);
        var savedCart = cartRepository.save(cart);
        entityManager.clear();

        assertThat(cartRepository.findById(savedCart.getId()).orElseThrow()).isEqualTo(cart);
    }

    @Test
    void checkCartUpdate() {
        var user = getCustomerUser();
        var cart = getCart(user);

        customerRepository.save(user);
        var savedCart = cartRepository.save(cart);
        entityManager.clear();
        savedCart.setName("cart");
        cartRepository.update(cart);
        entityManager.clear();

        assertThat(cartRepository.findById(cart.getId()).orElseThrow().getName()).isEqualTo("cart");
    }

    @Test
    void checkCartDeletion() {
        var user = getCustomerUser();
        var cart = getCart(user);

        customerRepository.save(user);
        var savedCart = cartRepository.save(cart);
        entityManager.clear();
        cartRepository.delete(savedCart);

        assertNull(cartRepository.findById(savedCart.getId()).orElse(null));
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

        List<Cart> carts = cartRepository.findAll();

        assertThat(carts.size()).isEqualTo(3);
        assertThatList(carts.stream().map(Cart::getName).toList()).contains("Second Nick Cart",
                "Alex cart", "first Nick Cart");
    }

    private Cart getCart(User user) {
        return Cart.builder()
                .name("myCart")
                .user(user)
                .build();
    }
}
