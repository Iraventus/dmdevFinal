package org.board_games_shop.mapper;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.CartGoodsCreateEditDto;
import org.board_games_shop.entity.Cart;
import org.board_games_shop.entity.CartGoods;
import org.board_games_shop.entity.Order;
import org.board_games_shop.entity.goods.Goods;
import org.board_games_shop.repository.CartRepository;
import org.board_games_shop.repository.GoodsRepository;
import org.board_games_shop.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartGoodsCreateEditMapper implements Mapper<CartGoodsCreateEditDto, CartGoods> {

    private final GoodsRepository goodsRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;


    @Override
    public CartGoods map(CartGoodsCreateEditDto object) {
        CartGoods cartGoods = new CartGoods();
        copy(object, cartGoods);
        return cartGoods;
    }

    @Override
    public CartGoods map(CartGoodsCreateEditDto fromObject, CartGoods toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(CartGoodsCreateEditDto object, CartGoods cartGoods) {
        cartGoods.setGoods(getGoods(object.getGoodsId()));
        cartGoods.setCart(getCart(object.getCartId()));
        cartGoods.setOrder(getOrder(object.getOrderId()));
        cartGoods.setTotalGoods(object.getTotalGoods());
    }

    public Goods getGoods(Long id) {
        return Optional.ofNullable(id)
                .flatMap(goodsRepository::findById)
                .orElse(null);
    }

    public Cart getCart(Long id) {
        return Optional.ofNullable(id)
                .flatMap(cartRepository::findById)
                .orElse(null);
    }

    public Order getOrder(Long id) {
        return Optional.ofNullable(id)
                .flatMap(orderRepository::findById)
                .orElse(null);
    }
}
