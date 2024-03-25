package org.boardGamesShop.repository;

import org.boardGamesShop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUserIdOrderByName(Long userId);
}
