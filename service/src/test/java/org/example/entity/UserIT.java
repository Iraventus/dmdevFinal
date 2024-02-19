package org.example.entity;

import org.example.entity.users.Customer;
import org.example.entity.users.Manager;
import org.example.entity.users.User;
import org.example.nodeModel.AddressNode;
import org.example.util.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserIT {

    @Test
    void checkCustomerCreation() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            var user = getCustomerUser();
            session.beginTransaction();
            session.persist(user);

            assertThat(session.get(User.class, user.getId())).isEqualTo(user);
            assertThat(session.get(User.class, user.getId()).getClass()).isEqualTo(Customer.class);
            session.getTransaction().commit();
        }
    }

    @Test
    void checkUserUpdate() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            var user = getCustomerUser();
            session.beginTransaction();
            session.persist(user);
            session.evict(user);
            user.setFirstname("Petrov");
            session.merge(user);

            assertThat(session.get(User.class, user.getId()).getFirstname()).isEqualTo("Petrov");
            session.getTransaction().commit();
        }
    }

    @Test
    void checkManagerCreation() {
        Configuration configuration = new Configuration();
        configuration.configure();
        var manager = Manager.builder()
                .login("Ivan@gmail1.com")
                .password("12345")
                .firstname("Ivan")
                .lastname("Ivanov")
                .birthDate(LocalDate.of(2000, 1, 19))
                .build();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.persist(manager);

            assertThat(session.get(User.class, manager.getId())).isEqualTo(manager);
            assertThat(session.get(User.class, manager.getId()).getClass()).isEqualTo(Manager.class);
            session.getTransaction().commit();
        }
    }

    @Test
    void checkUserDeletion() {
        Configuration configuration = new Configuration();
        configuration.configure();
        Customer user = getCustomerUser();
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.persist(user);
            session.remove(user);

            assertNull(session.get(User.class, user.getId()));
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
