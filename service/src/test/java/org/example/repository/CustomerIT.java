package org.example.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.example.util.DataImporterUtil.dataInit;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import org.example.config.ApplicationConfiguration;
import org.example.dto.UserFilter;
import org.example.entity.users.Customer;
import org.example.entity.users.User;
import org.example.nodeModel.AddressNode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CustomerIT {

  private final static AnnotationConfigApplicationContext context
      = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
  private static CustomerRepository customerRepository;
  private static EntityManager session;

  @BeforeAll
  static void dataPreparation() {
    customerRepository = context.getBean(CustomerRepository.class);
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
  void checkFindByCustomerID() {

    UserFilter filter = UserFilter.builder()
        .login("Nick@gmail.com")
        .build();
    var userWithLogin = customerRepository.findByFilters(session, filter).get(0);

    Customer user = customerRepository.findById(userWithLogin.getId()).orElseThrow();

    assertThat(user.getLogin()).isEqualTo("Nick@gmail.com");
    assertThat(user.getFirstname()).isEqualTo("Nick");
    assertThat(user.getClass()).isEqualTo(Customer.class);
  }

  @Test
  void checkFindManagerByFilters() {

    UserFilter filter = UserFilter.builder()
        .login("Nick@gmail.com")
        .firstName("Nick")
        .build();

    var user = customerRepository.findByFilters(session, filter).get(0);

    assertThat(user.getLogin()).isEqualTo("Nick@gmail.com");
    assertThat(user.getFirstname()).isEqualTo("Nick");
    assertThat(user.getClass()).isEqualTo(Customer.class);
  }

  @Test
  void checkCustomerCreation() {

    var customerUser = getCustomerUser();

    Customer customer = customerRepository.save(customerUser);
    session.clear();

    assertThat(customerRepository.findById(customer.getId()).orElseThrow()).isEqualTo(customerUser);
    assertThat(customerRepository.findById(customer.getId()).orElseThrow().getClass()).isEqualTo(
        Customer.class);

    customerRepository.delete(customer);
  }

  @Test
  void checkCustomerUserUpdate() {

    UserFilter filter = UserFilter.builder()
        .login("Alex@gmail.com")
        .build();
    var userWithLogin = customerRepository.findByFilters(session, filter).get(0);

    userWithLogin.setFirstname("Petr");
    customerRepository.update(userWithLogin);
    session.clear();

    assertThat(
        customerRepository.findById(userWithLogin.getId()).orElseThrow().getFirstname()).isEqualTo(
        "Petr");
  }

  @Test
  void checkCustomerDeletion() {

    var user = getCustomerUser();

    customerRepository.save(user);
    session.clear();
    customerRepository.delete(user);

    assertNull(customerRepository.findById(user.getId()).orElse(null));
  }

  @Test
  void checkAllCustomerUsersList() {

    List<Customer> users = customerRepository.findAll();

    assertThat(users.size()).isEqualTo(2);
    assertThatList(users.stream().map(User::getLogin).toList()).contains("Nick@gmail.com",
        "Alex@gmail.com");
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
