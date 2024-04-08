package org.board_games_shop.mapper;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.CartGoodsReadDto;
import org.board_games_shop.dto.CartReadDto;
import org.board_games_shop.dto.GoodsReadDto;
import org.board_games_shop.dto.OrderReadDto;
import org.board_games_shop.entity.CartGoods;
import org.board_games_shop.entity.goods.Accessories;
import org.board_games_shop.entity.goods.BoardGames;
import org.board_games_shop.entity.goods.Goods;
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
