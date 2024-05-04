package org.bgs.mapper;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.GoodsReadDto;
import org.bgs.dto.OrderGoodsReadDto;
import org.bgs.dto.OrderReadDto;
import org.bgs.entity.OrderGoods;
import org.bgs.entity.goods.Accessories;
import org.bgs.entity.goods.BoardGames;
import org.bgs.entity.goods.Goods;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderGoodsReadMapper implements Mapper<OrderGoods, OrderGoodsReadDto> {

    private final OrderReadMapper orderReadMapper;
    private final BoardGamesReadMapper boardGamesReadMapper;
    private final AccessoriesReadMapper accessoriesReadMapper;

    @Override
    public OrderGoodsReadDto map(OrderGoods orderGoods) {
        OrderReadDto order = Optional.ofNullable(orderGoods.getOrder())
                .map(orderReadMapper::map).orElse(null);
        GoodsReadDto goods = goodsMapper(orderGoods.getGoods());
        return new OrderGoodsReadDto(
                orderGoods.getId(),
                order,
                goods,
                orderGoods.getTotalGoods()
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
