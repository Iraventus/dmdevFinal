package org.board_games_shop.repository;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.UserFilter;
import org.board_games_shop.entity.users.Customer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class CustomerIT extends BaseIT {

    private final CustomerRepository customerRepository;

    @Test
    void checkFindCustomerByFilters() {
        UserFilter filter = new UserFilter("Nick@gmail.com", "Nick", null);

        List<Customer> user = customerRepository.findAllByFilter(filter);

        assertThat(user.size()).isEqualTo(1);
        assertThat(user.get(0).getLastname()).isEqualTo("Ivanov");
    }

    @Test
    void checkFindByLogin() {
        var customer = customerRepository.findByLogin("Nick@gmail.com");

        assertTrue(customer.isPresent());
        assertThat(customer.get().getId()).isEqualTo(1L);
    }
}
