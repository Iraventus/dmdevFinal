package org.bgs.service;

import lombok.RequiredArgsConstructor;
import org.bgs.dto.CartGoodsCreateEditDto;
import org.bgs.dto.CartGoodsReadDto;
import org.bgs.dto.CustomerReadDto;
import org.bgs.entity.Cart;
import org.bgs.entity.CartGoods;
import org.bgs.entity.goods.Goods;
import org.bgs.entity.users.Customer;
import org.bgs.mapper.CartGoodsCreateEditMapper;
import org.bgs.mapper.CartGoodsReadMapper;
import org.bgs.repository.CartGoodsRepository;
import org.bgs.repository.CartRepository;
import org.bgs.repository.CustomerRepository;
import org.bgs.repository.GoodsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartGoodsService {

    private final CartRepository cartRepository;
    private final GoodsRepository goodsRepository;
    private final CustomerRepository customerRepository;
    private final CartGoodsCreateEditMapper cartGoodsCreateEditMapper;
    private final CartGoodsReadMapper cartGoodsReadMapper;
    private final CartGoodsRepository cartGoodsRepository;

    public List<CartGoodsReadDto> findAll() {
        return cartGoodsRepository.findAll().stream()
                .map(cartGoodsReadMapper::map)
                .toList();
    }

    public Optional<CartGoodsReadDto> findById(Long id) {
        return cartGoodsRepository.findById(id)
                .map(cartGoodsReadMapper::map);
    }

    @Transactional
    public CartGoodsReadDto create(CartGoodsCreateEditDto cartGoodsCreateEditDto) {
        return Optional.of(cartGoodsCreateEditDto)
                .map(cartGoodsCreateEditMapper::map)
                .map(cartGoodsRepository::save)
                .map(cartGoodsReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public void addToCart(Long goodId, CustomerReadDto customerReadDto) {
        Cart cart = cartRepository.findByUserId(customerReadDto.getId())
                .orElse(null);
        if (cart == null) {
            Customer customer = customerRepository.findByLogin(customerReadDto.getLogin()).orElseThrow();
            cart = new Cart(customer, null);
            cartRepository.saveAndFlush(cart);
        }
        CartGoods cartGoods = cartGoodsRepository
                .findByGoodsIdAndCartId(goodId, cart.getId()).orElse(null);
        if (cartGoods == null) {
            Goods goods = goodsRepository.findById(goodId).orElseThrow();
            cartGoods = new CartGoods(goods, cart, null, 0);
        }
        cartGoods.setTotalGoods(cartGoods.getTotalGoods() + 1);
        cartGoodsRepository.saveAndFlush(cartGoods);
    }

    @Transactional
    public List<CartGoodsReadDto> showAllGoodsInCart(CustomerReadDto customerReadDto) {
        Cart cart = cartRepository.findByUserId(customerReadDto.getId())
                .orElse(null);
        if (cart == null) {
            Customer customer = customerRepository.findByLogin(customerReadDto.getLogin()).orElseThrow();
            cart = new Cart(customer, null);
            cartRepository.saveAndFlush(cart);
        }
        return cartGoodsRepository.findAllByCartId(cart.getId()).stream().map(cartGoodsReadMapper::map).toList();
    }

    @Transactional
    public Optional<CartGoodsReadDto> update(Long id, CartGoodsCreateEditDto cartGoodsCreateEditDto) {
        return cartGoodsRepository.findById(id)
                .map(entity -> cartGoodsCreateEditMapper.map(cartGoodsCreateEditDto, entity))
                .map(cartGoodsRepository::saveAndFlush)
                .map(cartGoodsReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return cartGoodsRepository.findById(id)
                .map(entity -> {
                    cartGoodsRepository.delete(entity);
                    cartGoodsRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
