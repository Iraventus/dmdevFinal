package org.bgs.mapper;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.OrderGoodsCreateEditDto;
import org.bgs.entity.Order;
import org.bgs.entity.OrderGoods;
import org.bgs.entity.goods.Goods;
import org.bgs.repository.GoodsRepository;
import org.bgs.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderGoodsCreateEditMapper implements Mapper<OrderGoodsCreateEditDto, OrderGoods> {

    private final GoodsRepository goodsRepository;
    private final OrderRepository orderRepository;

    @Override
    public OrderGoods map(OrderGoodsCreateEditDto object) {
        OrderGoods orderGoods = new OrderGoods();
        copy(object, orderGoods);
        return orderGoods;
    }

    @Override
    public OrderGoods map(OrderGoodsCreateEditDto fromObject, OrderGoods toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(OrderGoodsCreateEditDto object, OrderGoods orderGoods) {
        orderGoods.setGoods(getGoods(object.getGoodsId()));
        orderGoods.setOrder(getOrder(object.getOrderId()));
        orderGoods.setTotalGoods(object.getTotalGoods());
    }

    public Goods getGoods(Long id) {
        return Optional.ofNullable(id)
                .flatMap(goodsRepository::findById)
                .orElse(null);
    }

    public Order getOrder(Long id) {
        return Optional.ofNullable(id)
                .flatMap(orderRepository::findById)
                .orElse(null);
    }
}
