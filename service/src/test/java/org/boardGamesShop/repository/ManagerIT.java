package org.boardGamesShop.repository;

import lombok.RequiredArgsConstructor;
import org.boardGamesShop.dto.UserFilter;
import org.boardGamesShop.entity.users.User;
import org.boardGamesShop.entity.users.Manager;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertNull;

@RequiredArgsConstructor
public class ManagerIT extends BaseIT {

    private final ManagerRepository managerRepository;

    @Test
    void checkFindByManagerID() {
        UserFilter filter = UserFilter.builder()
                .login("Bob@gmail.com")
                .build();
        var userWithLogin = managerRepository.findByFilters(entityManager, filter).get(0);

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

        var user = managerRepository.findByFilters(entityManager, filter).get(0);

        assertThat(user.getLogin()).isEqualTo("Bob@gmail.com");
        assertThat(user.getFirstname()).isEqualTo("Bob");
        assertThat(user.getPersonalDiscount()).isEqualTo(20);
        assertThat(user.getClass()).isEqualTo(Manager.class);
    }

    @Test
    void checkManagerCreation() {
        var managerUser = getManagerUser();

        Manager manager = managerRepository.save(managerUser);
        entityManager.clear();

        assertThat(managerRepository.findById(manager.getId()).orElseThrow()).isEqualTo(managerUser);
        assertThat(managerRepository.findById(manager.getId()).orElseThrow().getClass()).isEqualTo(
                Manager.class);
    }

    @Test
    void checkManagerUserUpdate() {
        UserFilter filter = UserFilter.builder()
                .login("Bob@gmail.com")
                .build();
        var userWithLogin = managerRepository.findByFilters(entityManager, filter).get(0);

        userWithLogin.setFirstname("Petr");
        managerRepository.update(userWithLogin);
        entityManager.clear();

        assertThat(
                managerRepository.findById(userWithLogin.getId()).orElseThrow().getFirstname()).isEqualTo(
                "Petr");
    }

    @Test
    void checkManagerDeletion() {
        var user = getManagerUser();

        managerRepository.save(user);
        entityManager.clear();
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
