package org.example.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.example.util.DataImporterUtil.dataInit;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import org.example.config.ApplicationConfiguration;
import org.example.entity.Cart;
import org.example.entity.users.Customer;
import org.example.entity.users.User;
import org.example.nodeModel.AddressNode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class CartIT {

  private final static AnnotationConfigApplicationContext context
      = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
  private static CartRepository cartRepository;
  private static CustomerRepository customerRepository;
  private static EntityManager session;

  @BeforeAll
  static void dataPreparation() {
    customerRepository = context.getBean(CustomerRepository.class);
    cartRepository = context.getBean(CartRepository.class);
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
  void checkFindByIdCart() {

    var cart = cartRepository.findById(1L);

    assertTrue(cart.isPresent());
    assertThat(cart.get().getName()).isEqualTo("first Nick Cart");
  }

  @Test
  void checkCartCreation() {

    var user = getCustomerUser();
    var cart = getCart(user);

    customerRepository.save(user);
    var savedCart = cartRepository.save(cart);
    session.clear();

    assertThat(cartRepository.findById(savedCart.getId()).orElseThrow()).isEqualTo(cart);

    cartRepository.delete(savedCart);
  }

  @Test
  void checkCartUpdate() {

    var user = getCustomerUser();
    var cart = getCart(user);

    customerRepository.save(user);
    var savedCart = cartRepository.save(cart);
    session.clear();

    savedCart.setName("cart");
    cartRepository.update(cart);
    session.clear();

    assertThat(cartRepository.findById(cart.getId()).orElseThrow().getName()).isEqualTo("cart");

    cartRepository.delete(cart);
  }

  @Test
  void checkCartDeletion() {

    var user = getCustomerUser();
    var cart = getCart(user);

    customerRepository.save(user);
    var savedCart = cartRepository.save(cart);
    session.clear();

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
