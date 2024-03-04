package org.example.dao;

import org.example.dto.UserFilter;
import org.example.entity.users.Customer;
import org.example.entity.users.Manager;
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

public class UserRepositoryIT {

    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();
    private final Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
            new Class[]{Session.class}, (proxy, method, args1) -> method.invoke(SESSION_FACTORY.getCurrentSession(), args1));
    private UserRepository userRepository = null;


    @BeforeAll
    static void initDb() {
        TestDataImporter.importData(SESSION_FACTORY);
    }

    @BeforeEach
    void getTransaction() {
        session.beginTransaction();
        userRepository = new UserRepository(session);
    }

    @AfterEach
    void commitTransaction() {
        session.getTransaction().commit();
    }

    @Test
    void checkFindByIdUser() {

        UserFilter filter = UserFilter.builder()
                .login("Bob@gmail.com")
                .build();
        var userWithLogin = userRepository.findByFilters(session, filter).get(0);

        Manager user = (Manager) userRepository.findById(userWithLogin.getId()).orElseThrow();

        assertThat(user.getLogin()).isEqualTo("Bob@gmail.com");
        assertThat(user.getFirstname()).isEqualTo("Bob");
        assertThat(user.getPersonalDiscount()).isEqualTo(20);
        assertThat(user.getClass()).isEqualTo(Manager.class);
    }

    @Test
    void checkFindUserByFilters() {

        UserFilter filter = UserFilter.builder()
                .login("Bob@gmail.com")
                .firstName("Bob")
                .build();

        var user = (Manager) userRepository.findByFilters(session, filter).get(0);

        assertThat(user.getLogin()).isEqualTo("Bob@gmail.com");
        assertThat(user.getFirstname()).isEqualTo("Bob");
        assertThat(user.getPersonalDiscount()).isEqualTo(20);
        assertThat(user.getClass()).isEqualTo(Manager.class);
    }

    @Test
    void checkCustomerCreation() {

        var customerUser = getCustomerUser();

        Customer customer = (Customer) userRepository.save(customerUser);
        session.clear();

        assertThat(userRepository.findById(customer.getId()).orElseThrow()).isEqualTo(customerUser);
        assertThat(userRepository.findById(customer.getId()).orElseThrow().getClass()).isEqualTo(Customer.class);

        userRepository.delete(customer.getId());
    }

    @Test
    void checkManagerCreation() {

        var managerUser = getManagerUser();

        Manager manager = (Manager) userRepository.save(managerUser);
        session.clear();

        assertThat(userRepository.findById(manager.getId()).orElseThrow()).isEqualTo(managerUser);
        assertThat(userRepository.findById(manager.getId()).orElseThrow().getClass()).isEqualTo(Manager.class);

        userRepository.delete(manager.getId());
    }

    @Test
    void checkCustomerUserUpdate() {

        UserFilter filter = UserFilter.builder()
                .login("Alex@gmail.com")
                .build();
        var userWithLogin = userRepository.findByFilters(session, filter).get(0);

        userWithLogin.setFirstname("Petr");
        userRepository.update(userWithLogin);
        session.evict(userWithLogin);

        assertThat(userRepository.findById(userWithLogin.getId()).orElseThrow().getFirstname()).isEqualTo("Petr");
    }

    @Test
    void checkCustomerDeletion() {

        var user = getCustomerUser();

        userRepository.save(user);
        session.clear();
        userRepository.delete(user.getId());

        assertNull(userRepository.findById(user.getId()).orElse(null));
    }

    @Test
    void checkAllUsersList() {

        List<User> users = userRepository.findAll();

        assertThat(users.size()).isEqualTo(3);
        assertThatList(users.stream().map(User::getLogin).toList()).contains("Nick@gmail.com", "Alex@gmail.com", "Bob@gmail.com");
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

    private Manager getManagerUser() {
        return Manager.builder()
                .login("Seva@gmail1.com")
                .password("12345")
                .firstname("Seva")
                .lastname("Ivanov")
                .birthDate(LocalDate.of(2000, 1, 19))
                .personalDiscount(20)
                .build();
    }
}
