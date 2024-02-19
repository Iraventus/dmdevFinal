package org.example.entity;

import org.example.entity.users.Customer;
import org.example.nodeModel.AddressNode;
import org.example.util.HibernateTestUtil;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CartIT {

    @Test
    void checkCartCreation() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            var user = getCustomerUser();
            var cart = Cart.builder()
                    .name("myCart")
                    .user(getCustomerUser())
                    .build();

            session.beginTransaction();
            session.persist(user);
            session.persist(cart);

            assertThat(session.get(Cart.class, cart.getId())).isEqualTo(cart);
            assertThat(session.get(Cart.class, cart.getId()).getUser()).isEqualTo(user);
            session.getTransaction().commit();
        }
    }

    @Test
    void checkCartWithoutUserCreation() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            var cart = Cart.builder()
                    .name("myCart")
                    .build();

            session.beginTransaction();

            assertThrows(PropertyValueException.class, () -> session.persist(cart));
            session.getTransaction().commit();
        }
    }

    @Test
    void checkCartUpdate() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            var user = getCustomerUser();
            var cart = Cart.builder()
                    .name("myCart")
                    .user(user)
                    .build();

            session.beginTransaction();
            session.persist(user);
            session.persist(cart);
            cart.setName("cart");
            session.evict(cart);
            session.merge(cart);

            assertThat(session.get(Cart.class, cart.getId()).getName()).isEqualTo("cart");
            session.getTransaction().commit();
        }
    }

    @Test
    void checkCartDeletion() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            var user = getCustomerUser();
            var cart = Cart.builder()
                    .name("myCart")
                    .user(getCustomerUser())
                    .build();

            session.beginTransaction();
            session.persist(user);
            session.persist(cart);
            session.remove(cart);

            assertNull(session.get(Cart.class, cart.getId()));
            session.getTransaction().commit();
        }
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
