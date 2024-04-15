package org.bgs.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class CartIT extends BaseIT {

    private final CartRepository cartRepository;

    @Test
    void checkFindByUserId() {
        var cart = cartRepository.findByUserId(1L);

        assertTrue(cart.isPresent());
        assertThat(cart.get().getId()).isEqualTo(1L);
    }
}
