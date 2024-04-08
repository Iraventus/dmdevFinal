package org.board_games_shop.repository;

import org.board_games_shop.entity.goods.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
