package org.bgs.repository;

import org.bgs.entity.CartGoods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartGoodsRepository extends JpaRepository<CartGoods, Long> {

    List<CartGoods> findAllByCartId(Long id);
}
