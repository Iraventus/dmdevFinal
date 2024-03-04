package org.example.dao;

import org.example.entity.Cart;
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
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CartIT {

    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();
    private final Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
            new Class[]{Session.class}, (proxy, method, args1) -> method.invoke(SESSION_FACTORY.getCurrentSession(), args1));
    private CartRepository cartRepository = null;

    @BeforeAll
    static void initDb() {
        TestDataImporter.importData(SESSION_FACTORY);
    }

    @BeforeEach
    void getTransaction() {
        session.beginTransaction();
        cartRepository = new CartRepository(session);
    }

    @AfterEach
    void commitTransaction() {
        session.getTransaction().commit();
    }

    @Test
    void checkFindByIdCart() {

        var cart = cartRepository.findById(1L);

        assertTrue(cart.isPresent());
        assertThat(cart.get().getName()).isEqualTo("first Nick Cart");
    }

    @Test
    void checkCartCreation() {

        UserRepository userRepository = new UserRepository(session);
        var user = getCustomerUser();
        var cart = getCart(user);

        userRepository.save(user);
        var savedCart = cartRepository.save(cart);
        session.clear();

        assertThat(cartRepository.findById(savedCart.getId()).orElseThrow()).isEqualTo(cart);

        cartRepository.delete(savedCart.getId());
    }

    @Test
    void checkCartUpdate() {

        UserRepository userRepository = new UserRepository(session);
        var user = getCustomerUser();
        var cart = getCart(user);

        userRepository.save(user);
        var savedCart = cartRepository.save(cart);
        session.clear();

        savedCart.setName("cart");
        cartRepository.update(cart);
        session.evict(cart);

        assertThat(cartRepository.findById(cart.getId()).orElseThrow().getName()).isEqualTo("cart");

        cartRepository.delete(cart.getId());
    }

    @Test
    void checkCartDeletion() {

        UserRepository userRepository = new UserRepository(session);
        var user = getCustomerUser();
        var cart = getCart(user);

        userRepository.save(user);
        var savedCart = cartRepository.save(cart);
        session.clear();

        cartRepository.delete(savedCart.getId());

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
        assertThatList(carts.stream().map(Cart::getName).toList()).contains("Second Nick Cart", "Alex cart", "first Nick Cart");
    }

    private Cart getCart(User user) {
        return Cart.builder()
                .name("myCart")
                .user(user)
                .build();
    }
}
