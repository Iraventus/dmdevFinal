package org.bgs.mapper;

import lombok.RequiredArgsConstructor;
import org.bgs.entity.CartGoods;
import org.bgs.entity.goods.Accessories;
import org.bgs.entity.goods.BoardGames;
import org.bgs.dto.CartGoodsReadDto;
import org.bgs.dto.CartReadDto;
import org.bgs.dto.GoodsReadDto;
import org.bgs.dto.OrderReadDto;
import org.bgs.entity.goods.Goods;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartGoodsReadMapper implements Mapper<CartGoods, CartGoodsReadDto> {

    private final BoardGamesReadMapper boardGamesReadMapper;
    private final AccessoriesReadMapper accessoriesReadMapper;
    private final CartReadMapper cartReadMapper;
    private final OrderReadMapper orderReadMapper;


    @Override
    public CartGoodsReadDto map(CartGoods object) {
        GoodsReadDto goods = goodsMapper(object.getGoods());
        CartReadDto cart = Optional.ofNullable(object.getCart())
                .map(cartReadMapper::map)
                .orElse(null);
        OrderReadDto order = Optional.ofNullable(object.getOrder())
                .map(orderReadMapper::map)
                .orElse(null);
        return new CartGoodsReadDto(
                object.getId(),
                goods,
                cart,
                order,
                object.getTotalGoods()
        );
    }

    private GoodsReadDto goodsMapper(Goods goods) {
        if (goods.getClass().equals(BoardGames.class)) {
            return boardGamesReadMapper.map((BoardGames) goods);

        }
        if (goods.getClass().equals(Accessories.class)) {
            return accessoriesReadMapper.map((Accessories) goods);
        }
        return null;
    }
}
