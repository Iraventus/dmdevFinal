package org.board_games_shop.repository;

import org.board_games_shop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUserIdOrderByName(Long userId);
}
