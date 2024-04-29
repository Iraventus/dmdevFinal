package org.bgs.mapper;

import lombok.RequiredArgsConstructor;
import org.bgs.entity.Cart;
import org.bgs.entity.CartGoods;
import org.bgs.entity.Order;
import org.bgs.repository.CartRepository;
import org.bgs.repository.GoodsRepository;
import org.bgs.repository.OrderRepository;
import org.bgs.dto.CartGoodsCreateEditDto;
import org.bgs.entity.goods.Goods;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartGoodsCreateEditMapper implements Mapper<CartGoodsCreateEditDto, CartGoods> {

    private final GoodsRepository goodsRepository;
    private final CartRepository cartRepository;


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
}
