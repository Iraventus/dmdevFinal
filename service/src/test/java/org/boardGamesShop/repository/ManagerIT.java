package org.boardGamesShop.repository;

import lombok.RequiredArgsConstructor;
import org.boardGamesShop.dto.UserFilter;
import org.boardGamesShop.entity.users.Customer;
import org.boardGamesShop.entity.users.Manager;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class ManagerIT extends BaseIT {

    private final ManagerRepository managerRepository;

    @Test
    void checkFindManagerByFilters() {
        UserFilter filter = new UserFilter("Bob@gmail.com", null, null);

        var user = managerRepository.findAllByFilter(filter);

        assertThat(user.size()).isEqualTo(1);
        assertThat(user.get(0).getLastname()).isEqualTo("Petrov");
    }

    @Test
    void checkFindByLogin() {
        var manager = managerRepository.findByLogin("Bob@gmail.com");

        assertTrue(manager.isPresent());
        assertThat(manager.get().getId()).isEqualTo(3L);
    }

    @Test
    void checkAllProducersSortedList() {
        var sortBy = Sort.sort(Customer.class);
        var sort = sortBy.by(Customer::getLogin);

        List<Manager> producers = managerRepository.findAllBy(sort);

        assertThatList(producers.stream().map(Manager::getFirstname).toList())
                .containsExactly("Bob");
    }
}
