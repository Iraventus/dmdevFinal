package org.boardGamesShop.repository;

import org.boardGamesShop.entity.CartGoods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartGoodsRepository extends JpaRepository<CartGoods, Long> {

    List<CartGoodsRepository> findAllByCartId(Long id);
}
