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
import org.example.entity.users.Manager;
import org.example.entity.users.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ManagerIT {

  private final static AnnotationConfigApplicationContext context
      = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
  private static ManagerRepository managerRepository;
  private static EntityManager session;

  @BeforeAll
  static void dataPreparation() {
    managerRepository = context.getBean(ManagerRepository.class);
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
  void checkFindByManagerID() {

    UserFilter filter = UserFilter.builder()
        .login("Bob@gmail.com")
        .build();
    var userWithLogin = managerRepository.findByFilters(session, filter).get(0);

    Manager user = managerRepository.findById(userWithLogin.getId()).orElseThrow();

    assertThat(user.getLogin()).isEqualTo("Bob@gmail.com");
    assertThat(user.getFirstname()).isEqualTo("Bob");
    assertThat(user.getPersonalDiscount()).isEqualTo(20);
    assertThat(user.getClass()).isEqualTo(Manager.class);
  }

  @Test
  void checkFindManagerByFilters() {

    UserFilter filter = UserFilter.builder()
        .login("Bob@gmail.com")
        .firstName("Bob")
        .build();

    var user = managerRepository.findByFilters(session, filter).get(0);

    assertThat(user.getLogin()).isEqualTo("Bob@gmail.com");
    assertThat(user.getFirstname()).isEqualTo("Bob");
    assertThat(user.getPersonalDiscount()).isEqualTo(20);
    assertThat(user.getClass()).isEqualTo(Manager.class);
  }

  @Test
  void checkManagerCreation() {

    var managerUser = getManagerUser();

    Manager manager = managerRepository.save(managerUser);
    session.clear();

    assertThat(managerRepository.findById(manager.getId()).orElseThrow()).isEqualTo(managerUser);
    assertThat(managerRepository.findById(manager.getId()).orElseThrow().getClass()).isEqualTo(
        Manager.class);

    managerRepository.delete(manager);
  }

  @Test
  void checkManagerUserUpdate() {

    UserFilter filter = UserFilter.builder()
        .login("Bob@gmail.com")
        .build();
    var userWithLogin = managerRepository.findByFilters(session, filter).get(0);

    userWithLogin.setFirstname("Petr");
    managerRepository.update(userWithLogin);
    session.clear();

    assertThat(
        managerRepository.findById(userWithLogin.getId()).orElseThrow().getFirstname()).isEqualTo(
        "Petr");
  }

  @Test
  void checkManagerDeletion() {

    var user = getManagerUser();

    managerRepository.save(user);
    session.clear();
    managerRepository.delete(user);

    assertNull(managerRepository.findById(user.getId()).orElse(null));
  }

  @Test
  void checkAllManagerUsersList() {

    List<Manager> users = managerRepository.findAll();

    assertThat(users.size()).isEqualTo(1);
    assertThatList(users.stream().map(User::getLogin).toList()).contains("Bob@gmail.com");
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
