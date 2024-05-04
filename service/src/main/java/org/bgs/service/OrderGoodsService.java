package org.bgs.service;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.OrderGoodsReadDto;
import org.bgs.entity.CartGoods;
import org.bgs.entity.OrderGoods;
import org.bgs.entity.goods.Goods;
import org.bgs.mapper.OrderGoodsReadMapper;
import org.bgs.repository.CartGoodsRepository;
import org.bgs.repository.GoodsRepository;
import org.bgs.repository.OrderGoodsRepository;
import org.bgs.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderGoodsService {

    private final OrderRepository orderRepository;
    private final GoodsRepository goodsRepository;
    private final OrderGoodsRepository orderGoodsRepository;
    private final CartGoodsRepository cartGoodsRepository;
    private final OrderGoodsReadMapper orderGoodsReadMapper;

    @Transactional
    public void createCardGoodsOrder(Long orderId, Long cartGoodsId) {
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setOrder(orderRepository.findById(orderId).
                orElseThrow());
        orderGoods.setTotalGoods(cartGoodsRepository.findById(cartGoodsId).
                map(CartGoods::getTotalGoods).
                orElseThrow());
        orderGoods.setGoods(goodsRepository.
                findById(
                        cartGoodsRepository.findById(cartGoodsId).
                                map(CartGoods::getGoods).
                                map(Goods::getId).
                                orElseThrow()
                ).orElseThrow()
        );
        cartGoodsRepository.delete(cartGoodsRepository.
                findById(cartGoodsId).orElseThrow());
        orderGoodsRepository.saveAndFlush(orderGoods);
    }

    public List<OrderGoodsReadDto> findAllByOrderId(Long orderId) {
        return orderGoodsRepository.findAllByOrderId(orderId).stream()
                .map(orderGoodsReadMapper::map)
                .toList();
    }
}
