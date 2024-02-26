package org.example.entity;

import org.example.entity.users.Customer;
import org.example.nodeModel.AddressNode;
import org.example.util.HibernateTestUtil;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CartIT {

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
    void checkCartCreation() {

        var user = getCustomerUser();
        var cart = Cart.builder()
                .name("myCart")
                .user(user)
                .build();

        session.persist(user);
        session.persist(cart);
        session.clear();

        assertThat(session.get(Cart.class, cart.getId()).getId()).isEqualTo(cart.getId());
        assertThat(session.get(Cart.class, cart.getId()).getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    void checkCartWithoutUserCreation() {

        var cart = Cart.builder()
                .name("myCart")
                .build();

        assertThrows(PropertyValueException.class, () -> session.persist(cart));
    }

    @Test
    void checkCartUpdate() {
        var user = getCustomerUser();
        var cart = Cart.builder()
                .name("myCart")
                .user(user)
                .build();

        session.persist(user);
        session.persist(cart);
        cart.setName("cart");
        session.evict(cart);
        session.merge(cart);

        assertThat(session.get(Cart.class, cart.getId()).getName()).isEqualTo("cart");
    }

    @Test
    void checkCartDeletion() {

        var user = getCustomerUser();
        var cart = Cart.builder()
                .name("myCart")
                .user(user)
                .build();

        session.persist(user);
        session.persist(cart);
        session.clear();
        session.remove(cart);

        assertNull(session.get(Cart.class, cart.getId()));
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
