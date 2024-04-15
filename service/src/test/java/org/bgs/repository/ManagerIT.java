package org.bgs.repository;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.UserFilter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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
}
