package org.bgs.repository;

import org.bgs.entity.CartGoods;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartGoodsRepository extends JpaRepository<CartGoods, Long> {

    @EntityGraph(attributePaths = {"goods"})
    List<CartGoods> findAllByCartId(Long id);
    Optional<CartGoods> findByGoodsIdAndCartId(Long goodsId, Long cartId);
}
