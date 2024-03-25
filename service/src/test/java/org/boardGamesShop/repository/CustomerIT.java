package org.boardGamesShop.repository;

import lombok.RequiredArgsConstructor;
import org.boardGamesShop.dto.UserFilter;
import org.boardGamesShop.entity.users.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class CustomerIT extends BaseIT {

    private final CustomerRepository customerRepository;

    @Test
    void checkFindCustomerByFilters() {
        UserFilter filter = new UserFilter("Nick@gmail.com", "Nick", null);

        var user = customerRepository.findAllByFilter(filter);

        assertThat(user.size()).isEqualTo(1);
        assertThat(user.get(0).getLastname()).isEqualTo("Ivanov");
    }

    @Test
    void checkFindByLogin() {
        var customer = customerRepository.findByLogin("Nick@gmail.com");

        assertTrue(customer.isPresent());
        assertThat(customer.get().getId()).isEqualTo(1L);
    }

    @Test
    void checkAllProducersSortedList() {
        var sortBy = Sort.sort(Customer.class);
        var sort = sortBy.by(Customer::getLogin);

        List<Customer> producers = customerRepository.findAllBy(sort);

        assertThatList(producers.stream().map(Customer::getFirstname).toList())
                .containsExactly("Alex", "Nick");
    }
}
