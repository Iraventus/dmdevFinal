package org.example.entity;

import org.example.entity.users.Customer;
import org.example.entity.users.Manager;
import org.example.entity.users.User;
import org.example.nodeModel.AddressNode;
import org.example.util.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserIT {

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
    void checkCustomerCreation() {

        var user = getCustomerUser();
        session.persist(user);
        session.evict(user);

        assertThat(session.get(User.class, user.getId()).getId()).isEqualTo(user.getId());
        assertThat(session.get(User.class, user.getId()).getClass()).isEqualTo(Customer.class);
    }

    @Test
    void checkUserUpdate() {

        var user = getCustomerUser();
        session.persist(user);
        session.evict(user);
        user.setFirstname("Petrov");
        session.merge(user);

        assertThat(session.get(User.class, user.getId()).getFirstname()).isEqualTo("Petrov");
    }

    @Test
    void checkManagerCreation() {

        var manager = Manager.builder()
                .login("Ivan@gmail1.com")
                .password("12345")
                .firstname("Ivan")
                .lastname("Ivanov")
                .birthDate(LocalDate.of(2000, 1, 19))
                .build();

        session.persist(manager);
        session.evict(manager);

        assertThat(session.get(User.class, manager.getId()).getId()).isEqualTo(manager.getId());
        assertThat(session.get(User.class, manager.getId()).getClass()).isEqualTo(Manager.class);
    }

    @Test
    void checkUserDeletion() {
        Customer user = getCustomerUser();

        session.persist(user);
        session.remove(user);

        assertNull(session.get(User.class, user.getId()));
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
